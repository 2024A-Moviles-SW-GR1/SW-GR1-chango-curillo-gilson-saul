package com.example.a2024aswgr1gsch

import android.app.Activity
import android.content.pm.PackageManager
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ggoogle_maps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_google_maps)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        solicitarPermisos()
        iniciarLogicaMapa()
        val botonCarolina = findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina.setOnClickListener {
            val carolina = LatLng(-0.1755181190138262, -78.47918808450619)
            val zoom = 17f
            moverCamaraConZoom(carolina, zoom)
        }
    }
    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine =  ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine,nombrePermisoCoarse),1
            )
        }
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                anadirPolilineal()
                anadirPoligono()
                escucharListener()
            }
        }
    }
    fun mostraSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_blist_view),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
    fun escucharListener(){
        mapa.setOnPolygonClickListener{
            mostraSnackbar("setOnPolygonClickListener ${it.tag}")
        }
        mapa.setOnPolygonClickListener{
            mostraSnackbar("setOnPolygonClickListener ${it.tag}")
        }
        mapa.setOnMarkerClickListener{
            mostraSnackbar("setOnMapClickListener $it.tag")
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener{
            mostraSnackbar("setOnCameraMoveListener ")
        }
        mapa.setOnCameraMoveStartedListener{
            mostraSnackbar("setOnCameraMoveStartedListener")
        }
        mapa.setOnCameraIdleListener{
            mostraSnackbar("setOnCameraIdleListener")
        }

    }

    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.1755181190138262, -78.47918808450619
        )
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador(quicentro, titulo)
        markQuicentro.tag = titulo
        moverCamaraConZoom(quicentro, zoom)
    }

    fun anadirPolilineal(){
        with(mapa){
            val polilinealUno = mapa.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.1755181190138262, -78.47918808450619)

                    )
            )
            polilinealUno.tag = "Polilinea-uno"
        }
    }

    fun anadirPoligono(){
        with(mapa){
            PolygonOptions().clickable(true)
                .add(
                    LatLng(-0.1755181190138262, -78.47918808450619)
                )
        }
    }

    fun anadirMarcador(latLang: LatLng, title: String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLang)
                .title(title)
        )!!
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine =  ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 10f){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
            latLang, zoom
        ))
    }
}