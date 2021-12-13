package com.example.driverapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import com.google.firebase.database.*

class ViewStdDetail : AppCompatActivity() {
    var callBack: Button? = null
    var view_Driver: TextView? = null
    var id: Int = 0

    var view: View? = null
    private val Request_Call = 1

    var FullName: String? = ""
    var H_Address: String? = ""
    var ilat: String? = ""
    var ilon: String? = ""
    var hlat: String? = ""
    var hlon: String? = ""
    var I_Address: String? = ""
    var Phone_No: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_std_detail)
        view_Driver = findViewById(R.id.view_Driver)
        view = findViewById(R.id.view)

        FullName = intent.getStringExtra("Name") ?: ""
        I_Address = intent.getStringExtra("iadd") ?: ""
        H_Address = intent.getStringExtra("hadd") ?: ""
        ilat = intent.getStringExtra("ilat") ?: ""
        ilon = intent.getStringExtra("ilon") ?: ""
        hlat = intent.getStringExtra("hlat") ?: ""
        hlon = intent.getStringExtra("hlon") ?: ""
        Phone_No = intent.getStringExtra("phone") ?: ""


        val s = SpannableStringBuilder()
            .bold { append("Name: ") }
            .append(FullName)
            .bold { append("\nHome Address: ") }
            .append(H_Address)
            .bold { append("\nInstitute Address: ") }
            .append(I_Address)
            .bold { append("\n" + "Phone Number: ") }
            .append(Phone_No)
            .append("\n")
        view_Driver!!.append(s)


    }

    fun callBack(view: View) {
        finish()
    }

    fun viewSchedule(view: View) {

        val intent = Intent(this, View_Schedule::class.java)
        intent.putExtra("phone", Phone_No)
        startActivity(intent);
    }

    fun viewonMap(view: View) {
        val intent = Intent(this, StudentMap::class.java)
        intent.putExtra("hlat", hlat)
        intent.putExtra("hlon", hlon)
        intent.putExtra("ilat", ilat)
        intent.putExtra("ilon", ilon)
        startActivity(intent);
    }
    fun makecallStudent(view: View) {
        makeDriverCall()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Request_Call) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeDriverCall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun makeDriverCall() {
        if (Phone_No!!.trim().length > 0) {
            if (ContextCompat.checkSelfPermission(
                    this@ViewStdDetail,
                    android.Manifest.permission.CALL_PHONE
                ) != (PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    Request_Call
                )
            } else {
                val dial = "tel:" + Phone_No
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        }
    }
}