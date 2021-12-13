package com.example.driverapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.bold
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.time.LocalDate
import java.util.*

class Salary : AppCompatActivity() {
    //For Validation Purpose
    var fuel_consumption: TextInputLayout? = null
    var maintenance: TextInputLayout? = null
    var km_travel: TextInputLayout? = null
    var _id: Int = 0
    var Phone_No: String? = null
    var van_number: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary)

        fuel_consumption = findViewById((R.id.fuel_consumption))
        maintenance = findViewById((R.id.maintenance))
        km_travel = findViewById((R.id.km_traveled))


        val sessionManager =
            DriverSessionManager(this@Salary, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        val fullName = userDetails[DriverSessionManager.KEY_NAME]
        Phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]


        val checkDriver: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").child(Phone_No!!).child("Van")
        checkDriver.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dsnapshot: DataSnapshot) {
                if (dsnapshot.exists()) {
                    van_number = dsnapshot.getValue(String::class.java)


                } else {
                    Toast.makeText(this@Salary, "No Van Assign yet!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Salary, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })


    }

    private fun validateFuelConsumption(): Boolean {
        val `val` = fuel_consumption!!.editText!!.text.toString().trim { it <= ' ' }
        return if (`val`.isEmpty() || `val`.equals("0")) {
            fuel_consumption!!.error = "Field can not be empty and not be 0"
            false
        } else {
            fuel_consumption!!.error = null
            fuel_consumption!!.isErrorEnabled = false
            true
        }
    }

    private fun validateKMTravel(): Boolean {
        val `val` = km_travel!!.editText!!.text.toString().trim { it <= ' ' }
        return if (`val`.isEmpty() || `val`.equals("0")) {
            km_travel!!.error = "Field can not be empty and not be 0"
            false
        } else {
            km_travel!!.error = null
            km_travel!!.isErrorEnabled = false
            true
        }
    }

    private fun validateMaintenance(): Boolean {
        val `val` = maintenance!!.editText!!.text.toString().trim { it <= ' ' }
        return if (`val`.equals("0")) {
            maintenance!!.error = "Field cannot be 0"
            false
        } else {
            maintenance!!.error = null
            maintenance!!.isErrorEnabled = false
            true
        }
    }


    fun callBack(view: View) {
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitMilage(view: View) {
        if (!validateKMTravel() || !validateFuelConsumption() || !validateMaintenance()) {
            return
        } else {
            var km: String = km_travel!!.editText!!.getText().toString().trim()
            val fc: String = fuel_consumption!!.editText!!.getText().toString().trim()
            var mc: String = maintenance!!.editText!!.getText().toString().trim()
            if(mc.isEmpty())
            {
                mc = "0"
            }

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)+1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            var date: String = LocalDate.of(year!!, month!!, day!!).toString()

            try {
                val ref = FirebaseDatabase.getInstance().getReference("Log")
                val addNewUser = MilageDBHelperClass(
                    fc,
                    mc,
                    Phone_No,
                    van_number,
                    km,
                )
                ref.child(Phone_No!!).child(date!!).setValue(addNewUser)


            } catch (e: Exception) {
                println(e.message)
            }
            Toast.makeText(
                this@Salary,
                "Log submitted successfully!",
                Toast.LENGTH_SHORT
            ).show()
            finish()


        }
    }
}