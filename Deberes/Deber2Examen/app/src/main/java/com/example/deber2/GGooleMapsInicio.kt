package com.example.deber2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.deber2.databinding.ActivityGgooleMapsInicioBinding

class GGooleMapsInicio : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGgooleMapsInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGgooleMapsInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Obtener las coordenadas del Intent
        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)
        val location = LatLng(latitud, longitud)

        // Añadir un marcador y mover la cámara
        mMap.addMarker(MarkerOptions().position(location).title("Ubicación de la tienda"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        // Configurar los controles del mapa
        mMap.uiSettings.isZoomControlsEnabled = true  // Habilitar controles de zoom
        mMap.uiSettings.isScrollGesturesEnabled = true // Habilitar desplazamiento
        mMap.uiSettings.isZoomGesturesEnabled = true   // Habilitar gestos de zoom
        mMap.uiSettings.isRotateGesturesEnabled = true // Habilitar gestos de rotación
    }
}

