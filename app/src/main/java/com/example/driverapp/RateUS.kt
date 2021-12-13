package com.example.driverapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.*

class RateUS : AppCompatActivity() {
    var suggesstion: TextInputLayout? = null
    var rating: RatingBar? = null
    var Phone_No: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        suggesstion = findViewById((R.id.suggesstion))
        rating = findViewById((R.id.rate_us))


        val sessionManager =
            DriverSessionManager(this@RateUS, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        val fullName = userDetails[DriverSessionManager.KEY_NAME]
        Phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitFeedback(view: View) {
        var rate: String = rating!!.getRating().toString().trim()
        var sugg: String = suggesstion!!.editText!!.getText().toString().trim()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        var date: String = LocalDate.of(year!!, month!!, day!!).toString()

        try {
            val ref = FirebaseDatabase.getInstance().getReference("Feedback")
            val addNewUser = RatingDBHelperClass(
                rate,
                sugg,
            )

            ref.child("Driver "+Phone_No!!).child(date!!).setValue(addNewUser)


        } catch (e: Exception) {
            println(e.message)
        }
        Toast.makeText(
            this@RateUS,
            "Feedback submitted successfully!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
    fun callBack(view: View) {
        finish()
    }
}