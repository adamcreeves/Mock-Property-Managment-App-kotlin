package com.example.mockpropertymanagmentapp.ui.auth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.adapters.AdapterFragments
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var adapterFragments: AdapterFragments
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        adapterFragments = AdapterFragments(supportFragmentManager)
        view_pager_register.adapter = adapterFragments
        tab_layout_register.setupWithViewPager(view_pager_register)
    }
}