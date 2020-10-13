package com.example.mockpropertymanagmentapp.ui.properties.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockpropertymanagmentapp.R
import kotlinx.android.synthetic.main.activity_property.*

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)
        init()
    }

    private fun init() {
        button_property_to_add_property.setOnClickListener{
            startActivity(Intent(this, AddNewPropertyActivity::class.java))
        }
    }


}