package com.example.taipeiparkinglots

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taipeiparkinglots.databinding.FragmentMainPageBinding
import com.example.taipeiparkinglots.ext.TWToLatLng
import com.example.taipeiparkinglots.ext.UnderlineSpannable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseUser

class MainPageFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    private val parkViewModel: ParkViewModel by viewModels()
    private val userDbViewModel: UserDbViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private var currentParkClicked: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = authViewModel.getCurrentUser()
        if (user == null) {
            Toast.makeText(requireContext(), getString(R.string.not_yet_login_msg), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
            return
        }

        handleViewByUserState(user)

        parkViewModel.reloadAllData()
        binding.reloadButton.setOnClickListener {
            parkViewModel.reloadAllData()
        }
        binding.addFavoriteButton.setOnClickListener { handleAddFavorite() }
        userDbViewModel.parks.observe(viewLifecycleOwner){ list ->
            Toast.makeText(requireContext(), list.toString(), Toast.LENGTH_LONG).show()
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        parkViewModel.allParkMap.observe(viewLifecycleOwner) { parkMap ->
            for ((_, park) in parkMap) {
                // 將TWD97二度分帶座標轉為經緯度座標
                val latLngDoubleArray = TWToLatLng.convert(park.tw97x, park.tw97y)
                val latLng = LatLng(latLngDoubleArray[0], latLngDoubleArray[1])

                val carAvailable = park.carAvailable.let {
                    if (it == null || it < 0) {
                        "- "
                    } else {
                        it.toString()
                    }
                }

                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(park.name)
                        .snippet("${park.area}\n${park.address}\n${getString(R.string.car_available, carAvailable, park.totalCar)}")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                marker?.tag = park.id
            }
        }

        val taipei = LatLng(25.09108, 121.5598)
        googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))
        googleMap.setOnMarkerClickListener { handleMarkerClick(it) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taipei, 11.5f))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun handleMarkerClick(marker: Marker): Boolean {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        marker.showInfoWindow()
        currentParkClicked = marker.tag?.toString()
        return true
    }

    private fun handleViewByUserState(user: FirebaseUser) {
        if (user.isAnonymous) {
            binding.personMsgTextView.text = getString(R.string.person_msg, "訪客")
            binding.logoutButton.text = UnderlineSpannable.getUnderlineSpannable(getString(R.string.guest_sign_out))
            binding.logoutButton.setOnClickListener { handleSignOutButton(isAnonymous = true) }
        } else {
            val userEmail = user.email.orEmpty()
            userDbViewModel.initUserDbByEmail(userEmail)
            binding.personMsgTextView.text = getString(R.string.person_msg, userEmail)
            binding.logoutButton.text = UnderlineSpannable.getUnderlineSpannable(getString(R.string.sign_out))
            binding.logoutButton.setOnClickListener { handleSignOutButton(isAnonymous = false) }
            currentParkClicked = null
        }
    }

    private fun handleSignOutButton(isAnonymous: Boolean) {
        authViewModel.signOut()

        if (isAnonymous) {
            Toast.makeText(requireContext(), getString(R.string.guest_signed_out_msg), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.signed_out_msg), Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(R.id.loginFragment)
    }

    private fun handleAddFavorite(){
        val user = authViewModel.getCurrentUser()
        if (user==null || user.isAnonymous){
            AlertDialog.Builder(requireContext())
                .setTitle("您是訪客")
                .setMessage("使用信箱登入後才能使用此功能")
                .setPositiveButton("確認", null)
                .show()
        } else if (currentParkClicked!=null){
            val park = parkViewModel.allParkMap.value?.get(currentParkClicked)
            if (park!=null){
                AlertDialog.Builder(requireContext())
                    .setTitle("加入常用清單")
                    .setMessage("是否將 ${park.name} 加入您的常用清單？")
                    .setPositiveButton("好") { _, _ -> handleAddEvent(park.id) }
                    .setNeutralButton("取消", null)
                    .show()
            }
        }
    }

    private fun handleAddEvent(parkId: String){
        userDbViewModel.addOneParkingLot(parkId)
    }
}