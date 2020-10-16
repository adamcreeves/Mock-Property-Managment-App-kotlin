package com.example.mockpropertymanagmentapp.ui.home


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.ui.otheractivities.*
import com.example.mockpropertymanagmentapp.ui.properties.activities.PropertyActivity
import com.example.mockpropertymanagmentapp.ui.todolist.activities.ToDoListActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*

class HomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        toolbar()
        button_home_logout.setOnClickListener{
            dialogLogout()
        }
        button_home_properties.setOnClickListener{
            startActivity(Intent(this, PropertyActivity::class.java))
        }
        button_home_todo_list.setOnClickListener{
            startActivity(Intent(this, ToDoListActivity::class.java))
        }
        button_home_collect_rent.setOnClickListener{
            startActivity(Intent(this, CollectRentActivity::class.java))
        }
        button_home_documents.setOnClickListener{
            startActivity(Intent(this, DocumentActivity::class.java))
        }
        button_home_tenants.setOnClickListener{
            startActivity(Intent(this, TenantsActivity::class.java))
        }
        button_home_transactions.setOnClickListener{
            startActivity(Intent(this, TransactionsActivity::class.java))
        }
        button_home_trips.setOnClickListener{
            startActivity(Intent(this, TripActivity::class.java))
        }
        button_home_alerts.setOnClickListener{
            startActivity(Intent(this, AlertsActivity::class.java))
        }
        button_home_vendors.setOnClickListener{
            startActivity(Intent(this, VendorsActivity::class.java))
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

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "Home Page"
        setSupportActionBar(toolbar)
    }

}