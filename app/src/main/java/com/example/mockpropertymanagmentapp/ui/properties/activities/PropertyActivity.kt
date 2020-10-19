package com.example.mockpropertymanagmentapp.ui.properties.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Property
import com.example.mockpropertymanagmentapp.data.repositories.PropertyRepository
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.app_bar.*

class PropertyActivity : AppCompatActivity() {
    private var adapterProperties: AdapterProperties? = null
    var myList: ArrayList<Property> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)
        init()
    }

    private fun init() {
        toolbar()
        adapterProperties = AdapterProperties(this, myList)
        PropertyRepository().getData(this, adapterProperties!!)
        if(myList.isNotEmpty()) {
            progress_bar_property.visibility = View.GONE
        }
        recycler_view_properties.layoutManager = LinearLayoutManager(this)
        recycler_view_properties.adapter = adapterProperties
        button_property_to_add_property.setOnClickListener {
            startActivity(Intent(this, AddNewPropertyActivity::class.java))
        }


    }

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "Properties"
        setSupportActionBar(toolbar)
    }
}