package com.example.driverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*

class View_Schedule : AppCompatActivity() {

    var mon_pickup: TextView? = null
    var mon_dropoff: TextView? = null
    var mon_leave: TextView? = null

    var tue_pickup: TextView? = null
    var tue_dropoff: TextView? = null
    var tue_leave: TextView? = null

    var wed_pickup: TextView? = null
    var wed_dropoff: TextView? = null
    var wed_leave: TextView? = null

    var thu_pickup: TextView? = null
    var thu_dropoff: TextView? = null
    var thu_leave: TextView? = null

    var fri_pickup: TextView? = null
    var fri_dropoff: TextView? = null
    var fri_leave: TextView? = null

    var sat_pickup: TextView? = null
    var sat_dropoff: TextView? = null
    var sat_leave: TextView? = null

    var Phone_No: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)

        Phone_No = intent.getStringExtra("phone") ?: ""


        //Hooks
        mon_pickup = findViewById((R.id.mon_pickup))
        mon_dropoff = findViewById((R.id.mon_dropoff))
        mon_leave = findViewById((R.id.mon_lea))

        tue_pickup = findViewById((R.id.tue_pickup))
        tue_dropoff = findViewById((R.id.tue_dropoff))
        tue_leave = findViewById((R.id.tue_lea))

        wed_pickup = findViewById((R.id.wed_pickup))
        wed_dropoff = findViewById((R.id.wed_dropoff))
        wed_leave = findViewById((R.id.wed_lea))

        thu_pickup = findViewById((R.id.thu_pickup))
        thu_dropoff = findViewById((R.id.thu_dropoff))
        thu_leave = findViewById((R.id.thu_lea))

        fri_pickup = findViewById((R.id.fri_pickup))
        fri_dropoff = findViewById((R.id.fri_dropoff))
        fri_leave = findViewById((R.id.fri_lea))

        sat_pickup = findViewById((R.id.sat_pickup))
        sat_dropoff = findViewById((R.id.sat_dropoff))
        sat_leave = findViewById((R.id.sat_lea))


        //DatabaseQuery
        val checkUser: Query =
            FirebaseDatabase.getInstance().getReference("Schedule").child(Phone_No!!)
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    mon_pickup!!.setText(snapshot.child("mon_pickup").getValue(String::class.java))
                    mon_dropoff!!.setText(snapshot.child("mon_dropoff").getValue(String::class.java))
                    mon_leave!!.setText(snapshot.child("mon_leave").getValue(String::class.java))

                    tue_pickup!!.setText(snapshot.child("tue_pickup").getValue(String::class.java))
                    tue_dropoff!!.setText(snapshot.child("tue_dropoff").getValue(String::class.java))
                    tue_leave!!.setText(snapshot.child("tue_leave").getValue(String::class.java))

                    wed_pickup!!.setText(snapshot.child("wed_pickup").getValue(String::class.java))
                    wed_dropoff!!.setText(snapshot.child("wed_dropoff").getValue(String::class.java))
                    wed_leave!!.setText(snapshot.child("wed_leave").getValue(String::class.java))

                    thu_pickup!!.setText(snapshot.child("thu_pickup").getValue(String::class.java))
                    thu_dropoff!!.setText(snapshot.child("thu_dropoff").getValue(String::class.java))
                    thu_leave!!.setText(snapshot.child("thu_leave").getValue(String::class.java))


                    fri_pickup!!.setText(snapshot.child("fri_pickup").getValue(String::class.java))
                    fri_dropoff!!.setText(snapshot.child("fri_dropoff").getValue(String::class.java))
                    fri_leave!!.setText(snapshot.child("fri_leave").getValue(String::class.java))


                    sat_pickup!!.setText(snapshot.child("sat_pickup").getValue(String::class.java))
                    sat_dropoff!!.setText(snapshot.child("sat_dropoff").getValue(String::class.java))
                    sat_leave!!.setText(snapshot.child("sat_leave").getValue(String::class.java))
                } else {
                    Toast.makeText(this@View_Schedule, "Schedule not created yet!", Toast.LENGTH_SHORT).show();
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@View_Schedule, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
    }

    fun callBack(view: View) {
        finish()
    }
}