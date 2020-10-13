package com.example.mockpropertymanagmentapp.ui.home


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.ui.otheractivities.PropertyActivity
import com.example.mockpropertymanagmentapp.ui.otheractivities.StartActivity
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.ui.otheractivities.ToDoListActivity
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
        button_to_add_task.setOnClickListener{
            startActivity(Intent(this, ToDoListActivity::class.java))
        }
    }

    private fun dialogLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setNegativeButton("No"
        ) { dialog, p1 -> dialog?.dismiss() }
        builder.setPositiveButton("Yes") { p0, p1 ->
            sessionManager.logout()
            startActivity(Intent(applicationContext, StartActivity::class.java))
        }
        val myAlertDialog = builder.create()
        myAlertDialog.show()
    }

}