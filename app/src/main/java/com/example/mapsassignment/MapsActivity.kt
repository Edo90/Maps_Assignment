package com.example.mapsassignment

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.security.Permission
import java.security.Permissions
import java.security.Provider
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager : LocationManager
    private lateinit var criteria: Criteria
    private val ZOOM_LEVEL = 16f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(listener)
        mMap.isMyLocationEnabled = true

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val lastKnownLocationLatLng = LatLng(lastKnowLocation!!.latitude,lastKnowLocation.longitude)
            mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(lastKnownLocationLatLng,ZOOM_LEVEL)))
        }

    }

    private val listener = GoogleMap.OnMapLongClickListener { p0 -> setAddressOnMarker(p0) }

    private fun setAddressOnMarker(location: LatLng?) {
        val geocode = Geocoder(applicationContext, Locale.getDefault())
        var address = ""
        try {
            val addressList = geocode.getFromLocation(location!!.latitude, location.longitude, 1)
            if (addressList != null && addressList.size > 0) {
                if (addressList[0].thoroughfare != null) {
                    address += addressList[0].thoroughfare
                    if (addressList[0].subThoroughfare != null) {
                        address += ", " + addressList[0].subThoroughfare
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (address == "") {
            address = "No Address"
        }

        mMap.addMarker(MarkerOptions().position(location!!).title(address))
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(location, ZOOM_LEVEL)))
    }
}
