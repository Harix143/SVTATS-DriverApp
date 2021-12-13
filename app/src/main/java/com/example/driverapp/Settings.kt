package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun callBack(view: View) {
        finish()
    }
    fun changePassword(view: View) {
        val intent = Intent(applicationContext, ChangePassword::class.java)
        startActivity(intent)
    }
}