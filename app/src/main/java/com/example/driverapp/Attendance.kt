package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Attendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)
    }

    fun callBack(view: View) {
        finish()
    }
    fun markpickup(view: View) {
        val intent = Intent(applicationContext, Pickup_Attendance::class.java)
        startActivity(intent)
    }
    fun markdropoff(view: View) {
        val intent = Intent(applicationContext, Dropoff_Attendance::class.java)
        startActivity(intent)
    }
}