package com.example.taipeiparkinglots

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.taipeiparkinglots.databinding.CustomInfoWindowBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(context: Context): GoogleMap.InfoWindowAdapter {

    private val binding = CustomInfoWindowBinding.inflate(LayoutInflater.from(context), null, false)

    override fun getInfoContents(marker: Marker): View {
        val view = binding.root
        render(marker)
        return view
    }

    override fun getInfoWindow(marker: Marker): View {
        val view = binding.root
        render(marker)
        return view
    }

    private fun render(marker: Marker){
        binding.title.text = marker.title
        binding.snippet.text = marker.snippet
    }
}