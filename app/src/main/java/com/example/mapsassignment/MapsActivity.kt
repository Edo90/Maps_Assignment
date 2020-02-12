package com.example.mapsassignment

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private var addressArrayList : ArrayList<LatLng>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //val databasedb: SQLLiteDatabase
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

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener{
            override fun onLocationChanged(location: Location?) {
                if(location != null){
                    mMap.clear()
                    val userLocation = LatLng(location.latitude,location.longitude)
                    setAddressOnMarker(userLocation)
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderEnabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderDisabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
//        }else{
//            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,10f,locationListener)
//
//            //val lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            var criteria = Criteria()
//            criteria.accuracy = Criteria.ACCURACY_FINE
//            val userBestProvider = locationManager!!.getBestProvider(criteria,true);
//
//            val userBestLocation = locationManager!!.getLastKnownLocation(userBestProvider)
//            var userLastLocation = LatLng(userBestLocation.latitude,userBestLocation.longitude)
//            setAddressOnMarker(userLastLocation)
        }

    private val listener = object : GoogleMap.OnMapLongClickListener{
        override fun onMapLongClick(p0: LatLng?) {
            setAddressOnMarker(p0)
        }

    }
    private fun setAddressOnMarker(location: LatLng?) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        var address = ""
        try {
            val addressList = geocoder.getFromLocation(location!!.latitude,location.longitude,1)
            if(addressList != null && addressList.size > 0){
                if(addressList[0].thoroughfare != null){
                    address += addressList[0].thoroughfare
                    if(addressList[0].subThoroughfare != null){
                        address += ", "+addressList[0].subThoroughfare
                    }
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
        if(address.equals("")){
            address = "No Address"
        }

        mMap.addMarker(MarkerOptions().position(location!!).title(address))
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(location,16f)))
    }

}
