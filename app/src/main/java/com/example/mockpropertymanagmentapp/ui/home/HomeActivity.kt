package com.example.mockpropertymanagmentapp.ui.home


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.otheractivities.*
import com.example.mockpropertymanagmentapp.ui.properties.activities.PropertyActivity
import com.example.mockpropertymanagmentapp.ui.todolist.activities.ToDoListActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.contact_bottom_sheet.*
import kotlinx.android.synthetic.main.contact_bottom_sheet.view.*

class HomeActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        toolbar()
        bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.contact_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        button_contact_us.setOnClickListener {
            bottomSheetDialog.show()
        }
        view.button_contact_send_email.setOnClickListener {
            val subject = view.edit_text_contact_subject.text.toString().trim()
            val message = view.edit_text_contact_message.text.toString().trim()
            val recipient = "adam@adamrdev.com".trim()
            if(subject != "" && message != ""){
                sendEmail(recipient, subject, message)
                bottomSheetDialog.dismiss()
            } else this.toastShort("You have to fill out both fields")
        }
        button_home_logout.setOnClickListener {
            dialogLogout()
        }
        button_home_properties.setOnClickListener {
            startActivity(Intent(this, PropertyActivity::class.java))
        }
        button_home_todo_list.setOnClickListener {
            startActivity(Intent(this, ToDoListActivity::class.java))
        }
        button_home_collect_rent.setOnClickListener {
            startActivity(Intent(this, CollectRentActivity::class.java))
        }
        button_home_documents.setOnClickListener {
            startActivity(Intent(this, DocumentActivity::class.java))
        }
        button_home_tenants.setOnClickListener {
            startActivity(Intent(this, TenantsActivity::class.java))
        }
        button_home_transactions.setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
        }
        button_home_trips.setOnClickListener {
            startActivity(Intent(this, TripActivity::class.java))
        }
        button_home_alerts.setOnClickListener {
            startActivity(Intent(this, AlertsActivity::class.java))
        }
        button_home_vendors.setOnClickListener {
            startActivity(Intent(this, VendorsActivity::class.java))
        }
    }

    private fun dialogLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setNegativeButton(
            "No"
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
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            e.message?.let { this.toastShort(it) }
        }

    }
}