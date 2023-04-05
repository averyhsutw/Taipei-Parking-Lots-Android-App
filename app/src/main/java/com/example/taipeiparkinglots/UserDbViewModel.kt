package com.example.taipeiparkinglots

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taipeiparkinglots.data.UserDb
import com.example.taipeiparkinglots.data.UserOperationError
import com.example.taipeiparkinglots.ext.Event
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserDbViewModel: ViewModel() {

    private lateinit var db: FirebaseFirestore
    private var userEmail: String? = null
    private val _parks = MutableLiveData<List<String>>()
    val parks: LiveData<List<String>> = _parks
    private val _operationError = MutableLiveData<Event<UserOperationError>>()
    val operationError: LiveData<Event<UserOperationError>> = _operationError

    fun initUserDbByEmail(email: String){
        userEmail = email
        db = Firebase.firestore
        getRemoteUserDoc()
    }

    private fun getRemoteUserDoc(){
        val docRef = db.collection(COLLECTION_NAME).document(userEmail.orEmpty())
        docRef.get().addOnSuccessListener { document ->
            if (document!=null){
                val userDb = document.toObject<UserDb>()
                if (userDb?.parks == null){
                    addRemoteUserDocWithEmpty()
                }

                // local init
                viewModelScope.launch {
                    _parks.value = userDb?.parks ?: listOf("")
                    Log.d(TAG, _parks.value.toString())
                }

                Log.d(TAG, "getDataFromRemote success: ${document.id} -> ${document.data}")
            } else {
                Log.d(TAG, "getDataFromRemote: document doesn't exist!")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failure", exception)
        }
    }

    private fun addRemoteUserDocWithEmpty(){
        val userDb = UserDb(listOf(""))
        db.collection(COLLECTION_NAME).document(userEmail.orEmpty())
            .set(userDb)
            .addOnFailureListener { exception ->
                Log.d(TAG, "add remote user with empty failure", exception)
            }
    }

    fun addOneParkingLot(parkId: String){
        // local
        val parkList = parks.value!!.toMutableList()
        if (parkId in parkList){
            // should not happen
            _operationError.value = Event(UserOperationError.AddRepeatedlyError)
            return
        } else {
            parkList.add(parkId)
            _parks.value = parkList
        }

        // remote
        val docRef = db.collection(COLLECTION_NAME).document(userEmail.orEmpty())
        docRef.update(FIELD_NAME, FieldValue.arrayUnion(parkId)).addOnFailureListener { exception ->
            Log.d(TAG, "add remote one failure", exception)
        }
    }

    fun removeOneParkingLot(parkId: String){
        // local
        val parkList = parks.value!!.toMutableList()
        if (parkId in parkList){
            parkList.remove(parkId)
            _parks.value
        } else{
            // should not happen
            _operationError.value = Event(UserOperationError.RemoveNonExistError)
            return
        }

        // remote
        val docRef = db.collection(COLLECTION_NAME).document(userEmail.orEmpty())
        docRef.update(FIELD_NAME, FieldValue.arrayRemove(parkId)).addOnFailureListener { exception ->
            Log.d(TAG, "remove remote one failure", exception)
        }
    }

    companion object{
        private const val COLLECTION_NAME = "users"
        private const val FIELD_NAME = "parks"
        private const val TAG="UserDbVM"
    }
}