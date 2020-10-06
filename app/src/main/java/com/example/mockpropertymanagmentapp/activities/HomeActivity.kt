package com.example.mockpropertymanagmentapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        button_to_add_property.setOnClickListener{
            startActivity(Intent(this, PropertyActivity::class.java))
        }
        button_home_logout.setOnClickListener{
            dialogLogout()
        }
    }

    private fun dialogLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                sessionManager.logout()
                startActivity(Intent(applicationContext, StartActivity::class.java))
            }
        })
        var myAlertDialog = builder.create()
        myAlertDialog.show()
    }

}