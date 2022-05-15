package com.example.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map:GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragment()
    }

    private fun createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        enableLocation()

        findViewById<ImageView>(R.id.ivRestaurante1).setOnClickListener{
            Toast.makeText(this,"Chili's", Toast.LENGTH_SHORT).show()
            createMarker(19.2993325, -99.1389257,"Chili's")
        }

        findViewById<ImageView>(R.id.ivRestaurante2).setOnClickListener{
            Toast.makeText(this,"Frito y cruel", Toast.LENGTH_SHORT).show()
            createMarker(19.4205831, -99.1735989,"Frito y cruel")
        }

        findViewById<ImageView>(R.id.ivRestaurante3).setOnClickListener{
            Toast.makeText(this,"Sonora Grill", Toast.LENGTH_SHORT).show()
            createMarker(19.3577016, -99.1900189,"Sonora Grill")
        }

        findViewById<ImageView>(R.id.ivRestaurante4).setOnClickListener{
            Toast.makeText(this,"Fat Vegan", Toast.LENGTH_SHORT).show()
            createMarker(19.4137792, -99.1606748,"Fat Vegan")
        }
    }

    private fun createMarker(lat: Double, lon: Double, ubicacion: String){
        val coordenadas = LatLng(lat,lon)
        val marker = MarkerOptions().position(coordenadas).title(ubicacion)

        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
            4000,null
        )
    }

    private fun enableLocation(){
        if(!::map.isInitialized)return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled=true
        } else {
            requestLocationPermission()
        }
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            //mostrar la ventana de permiso
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled=true
            } else {
                Toast.makeText(this,"Para activar permisos, ve a ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }
}