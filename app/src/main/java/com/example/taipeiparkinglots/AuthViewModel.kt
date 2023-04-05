package com.example.taipeiparkinglots

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taipeiparkinglots.ext.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

private const val TAG="User"

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoginSuccessful = MutableLiveData<Event<Boolean>>()
    val isLoginSuccessful: LiveData<Event<Boolean>> = _isLoginSuccessful

    private val _isAnonymousLoginSuccessful = MutableLiveData<Event<Boolean>>()
    val isAnonymousLoginSuccessful: LiveData<Event<Boolean>> = _isAnonymousLoginSuccessful

    private val _isSignupSuccessful = MutableLiveData<Event<Boolean>>()
    val isSignupSuccessful: LiveData<Event<Boolean>> = _isSignupSuccessful

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    fun signInAnonymously(){
        auth.signInAnonymously().addOnCompleteListener { task->
            if (task.isSuccessful){
                Log.d(TAG, "signInAnonymously: success")
                viewModelScope.launch {
                    _isAnonymousLoginSuccessful.value = Event(true)
                }
            }else{
                Log.w(TAG, "signInAnonymously:failure", task.exception)
                viewModelScope.launch {
                    _isAnonymousLoginSuccessful.value = Event(false)
                }
            }
        }
    }

    fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    Log.d(TAG, "sign up success")
                    viewModelScope.launch {
                        _isSignupSuccessful.value = Event(true)
                    }
                }else{
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    viewModelScope.launch {
                        _isSignupSuccessful.value = Event(false)
                    }
                }
            }
    }


    fun signInWithEmail(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    viewModelScope.launch {
                        _isLoginSuccessful.value = Event(true)
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    viewModelScope.launch {
                        _isLoginSuccessful.value = Event(false)
                    }
                }
            }
    }

    fun signOut(){
        auth.signOut()
    }
}