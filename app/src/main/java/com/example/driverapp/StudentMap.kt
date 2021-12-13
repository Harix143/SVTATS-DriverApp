package com.example.driverapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.driverapp.databinding.ActivityStudentMapBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class StudentMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStudentMapBinding
    private var hlat: Double = 0.0
    private var hlon: Double = 0.0
    private var ilat: Double = 0.0
    private var ilon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ilat = intent.getStringExtra("ilat")!!.toDouble()
        ilon = intent.getStringExtra("ilon")!!.toDouble()
        hlat = intent.getStringExtra("hlat")!!.toDouble()
        hlon = intent.getStringExtra("hlon")!!.toDouble()

        binding = ActivityStudentMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map1) as SupportMapFragment
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

        var hLoc = LatLng(hlat!!, hlon!!)
        var iLoc = LatLng(ilat!!, ilon!!)
        mMap.addMarker(MarkerOptions().position(hLoc).title("Home Location!")
            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_person_pin_24)))
        mMap.addMarker(MarkerOptions().position(iLoc).title( "School Location")
            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_location_city_24)))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.isTrafficEnabled = true;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hLoc, 12f))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(iLoc, 12f))

    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}