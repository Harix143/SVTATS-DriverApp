package com.example.driverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap

class Pickup_Attendance : AppCompatActivity() {

    var Phone_No: String? = null
    var recyclerView: RecyclerView? = null
    var adapter: PickupAttendanceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_attendance)

        val sessionManager =
            DriverSessionManager(
                this@Pickup_Attendance,
                DriverSessionManager.SESSION_USERSESSION
            )
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        Phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]

        recyclerView = findViewById((R.id.precycler_View))
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))

        val holder = ArrayList<StudentModel>()

        //DataBase Query
        val checkUser: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").child(Phone_No!!).child("students")
        checkUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()) {
                    for (childDS in snapshot.children) {

                        //progressBar!!.visibility = View.VISIBLE
                        val cm = StudentModel()
                        var obj = childDS.getValue(StudentDBHelperClass::class.java)
                        cm.setImg(R.drawable.profile)
                        cm.setName(obj!!.fullName)
                        cm.setPhone(obj!!.phone_No)
                        cm.setIlat(obj!!.ilat)
                        cm.setIlon(obj!!.ilon)
                        cm.setHlat(obj!!.hlat)
                        cm.setHlon(obj!!.hlon)
                        cm.setIadd(obj!!.iaddress)
                        cm.setHadd(obj!!.haddress)
                        cm.setDesc("Click to Mark the Pickup Attendance.")
                        holder.add(cm)
                        adapter = PickupAttendanceAdapter(holder, applicationContext)
                        recyclerView!!.setAdapter(adapter)
                    }
                }
                else{
                    Toast.makeText(this@Pickup_Attendance, "No Student Assigned Yet!", Toast.LENGTH_SHORT).show();
                    finish()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Pickup_Attendance, error.message, Toast.LENGTH_SHORT).show();
            }
        })

    }

    fun callBack(view: View) {
        finish()
    }
}