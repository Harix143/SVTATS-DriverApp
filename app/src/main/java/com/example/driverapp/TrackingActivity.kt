package com.example.driverapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.driverapp.databinding.ActivityTrackingBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import java.util.*

class TrackingActivity : AppCompatActivity(), OnMapReadyCallback , LocationListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityTrackingBinding
    private val REQUEST_CODE = 101
    private val MIN_TIME: Long = 1000 // 1 Sec
    private val MIN_DIS : Float = 1.0F // 1 Meter
    private var dbref: DatabaseReference? = null
    private var manager: LocationManager? = null
    private var marker: Marker? = null
    private var lat: Double = 33.6567
    private var lon: Double = 73.1598



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        //SessionManager
        val sessionManager = DriverSessionManager(this, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        val fullName = userDetails[DriverSessionManager.KEY_NAME]
        var phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]
//        if (phone_No != null) {
//            if (phone_No.get(0) == '0') {
//                phone_No = phone_No.substring(1)
//            }
//        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //DataBase Query
        val checkUser: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").child(phone_No!!).child("students")
        checkUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (childDS in snapshot.children) {

                        //progressBar!!.visibility = View.VISIBLE
                        var obj = childDS.getValue(StudentDBHelperClass::class.java)
                        var name = obj!!.fullName
                        var hlat = obj!!.hlat!!.toDouble();
                        var hlon = obj!!.hlon!!.toDouble();
                        var ilat = obj!!.ilat!!.toDouble();
                        var ilon = obj!!.ilon!!.toDouble();
                        var hLoc = LatLng(hlat!!, hlon!!)
                        var iLoc = LatLng(ilat!!, ilon!!)
                        mMap.addMarker(MarkerOptions().position(hLoc).title(name+" Location!")
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_person_pin_24)))
                        mMap.addMarker(MarkerOptions().position(iLoc).title(name+ " School")
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_location_city_24)))
                    }
                }
                else{
                    Toast.makeText(this@TrackingActivity, "No Student Assigned Yet!", Toast.LENGTH_SHORT).show();
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TrackingActivity, error.message, Toast.LENGTH_SHORT).show();
            }
        })

        dbref = FirebaseDatabase.getInstance().getReference("Drivers").child(phone_No!!).child("location");
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

//         Obtain the SupportMapFragment and get notified when the map is ready to be used.


        getLocationUpdates();
        readChanges()
    }

    private fun readChanges() {
        dbref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                    try {
                        run {
                            var dl: DriverLocation? = snapshot.getValue(DriverLocation::class.java)

                            if(dl != null)
                            {
                                marker!!.setPosition(LatLng(dl.latitude, dl.longitude))
                                lat = dl.latitude;
                                lon = dl.longitude;
                            }
                        }
                    }
                    catch (e: Exception)
                    {
                        Toast.makeText(this@TrackingActivity, e.toString(), Toast.LENGTH_SHORT)
                            .show();
                    }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TrackingActivity, error.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            }
        })
    }
    private fun getLocationUpdates() {
        if(manager != null)
        {
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED       ) {
                if (manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME,
                        MIN_DIS,
                        this
                    )
                } else if (manager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME,
                        MIN_DIS,
                        this
                    )
                } else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
                    Toast.makeText(this, "Please Turn ON Location", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 101)
        {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getLocationUpdates()
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
//                Toast.makeText(this,"Permission Required", Toast.LENGTH_SHORT).show()
            }
        }
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


    override fun onLocationChanged(p0: Location) {
        if(p0 != null)
        {
            savLocation(p0);
        }
        else
        {
            Toast.makeText(this,"No Location",Toast.LENGTH_SHORT).show()
        }
    }

    private fun savLocation(p0: Location) {
        dbref!!.setValue(p0);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val islamabad = LatLng(lat, lon)
        marker = mMap.addMarker(MarkerOptions().position(islamabad).title("My Location!")
            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_driver_directions_bus_24)))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.isTrafficEnabled = true;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(islamabad, 10f))

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