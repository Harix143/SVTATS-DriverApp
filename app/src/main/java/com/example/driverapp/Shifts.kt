package com.example.driverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import com.google.firebase.database.*
import java.util.HashMap

class Shifts : AppCompatActivity() {

    var view_Driver: TextView? = null
    var phone_No: String? = null
    var van_number: String? = null
    var driver_name: String? = null
    var model: String? = null
    var ac: String? = null
    var capacity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shifts)

        view_Driver = findViewById(R.id.view_Driver)

        val sessionManager =
            DriverSessionManager(this@Shifts, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        driver_name = userDetails[DriverSessionManager.KEY_NAME]
        phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]


        val checkDriver: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").child(phone_No!!).child("Van")
        checkDriver.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dsnapshot: DataSnapshot) {
                if (dsnapshot.exists()) {
                    van_number = dsnapshot.getValue(String::class.java)

                    val checkVan: Query =
                        FirebaseDatabase.getInstance().getReference("Vans").child(dsnapshot.getValue(String::class.java)!!)
                    checkVan.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(vsnapshot: DataSnapshot) {
                            if (vsnapshot.exists()) {
                                ac = vsnapshot.child("ac").getValue(String::class.java)
                                capacity = vsnapshot.child("capacity").getValue(String::class.java)
                                model = vsnapshot.child("model").getValue(String::class.java)

                                val s = SpannableStringBuilder()
                                    .bold { append("Vehicle No: ") }
                                    .append(van_number)
                                    .bold { append("\nDriver Name: ") }
                                    .append(driver_name)
                                    .bold { append("\n" + "Model: ") }
                                    .append(model)
                                    .bold { append("\n" + "Air-Condition: ") }
                                    .append(ac)
                                    .bold { append("\n" + "Capacity: ") }
                                    .append(capacity)
                                    .append("\n")
                                view_Driver!!.append(s)

                            } else {
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@Shifts, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                } else {
                    Toast.makeText(this@Shifts, "No Van Assign yet!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Shifts, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })

    }

    fun callBack(view: View) {
        finish()
    }
}