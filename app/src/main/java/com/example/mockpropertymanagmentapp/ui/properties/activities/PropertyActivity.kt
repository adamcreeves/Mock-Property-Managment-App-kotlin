package com.example.mockpropertymanagmentapp.ui.properties.activities

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.PropertiesResponse
import com.example.mockpropertymanagmentapp.data.models.Property
import com.example.mockpropertymanagmentapp.data.models.Task
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.data.repositories.PropertyRepository
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesListener
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesViewModel
import com.example.mockpropertymanagmentapp.ui.properties.PropertyListListener
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.property_bottom_sheet.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        progress_bar_property.visibility = View.GONE
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