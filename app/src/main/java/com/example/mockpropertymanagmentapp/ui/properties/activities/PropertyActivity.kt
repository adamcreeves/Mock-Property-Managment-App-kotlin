package com.example.mockpropertymanagmentapp.ui.properties.activities

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.PropertiesResponse
import com.example.mockpropertymanagmentapp.data.models.Property
import com.example.mockpropertymanagmentapp.databinding.ActivityPropertyBinding
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesListener
import com.example.mockpropertymanagmentapp.ui.properties.PropertiesViewModel
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
import kotlinx.android.synthetic.main.property_bottom_sheet.view.*

class PropertyActivity : AppCompatActivity(), PropertiesListener {
    private var adapterProperties: AdapterProperties? = null
    var myList: ArrayList<Property> = ArrayList()
    lateinit var binding: ActivityPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property)
        init()
    }

    private fun init() {
        var viewModel = ViewModelProviders.of(this).get(PropertiesViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.propertiesListener = this
        adapterProperties = AdapterProperties(this, myList)
        recycler_view_properties.layoutManager = LinearLayoutManager(this)
        recycler_view_properties.adapter = adapterProperties

        button_property_to_add_property.setOnClickListener {
            startActivity(Intent(this, AddNewPropertyActivity::class.java))
        }


    }

    override fun onStarted() {
        Toast.makeText(this, "API call has begun", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessful(response: LiveData<ArrayList<Property>>) {
        response.observe(this, Observer {
            adapterProperties?.setData(it)
            Toast.makeText(this, "Properties Retrieved", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}