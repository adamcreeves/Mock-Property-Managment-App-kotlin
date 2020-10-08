package com.example.mockpropertymanagmentapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mockpropertymanagmentapp.ui.auth.fragments.RegisterLandlordFragment
import com.example.mockpropertymanagmentapp.ui.auth.fragments.RegisterTenantFragment

class AdapterFragments(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RegisterLandlordFragment()
            else -> RegisterTenantFragment()

        }
    }

    override fun getPageTitle(position: Int) : CharSequence? {
        return when(position){
            0 -> "Landlord"
            else -> "Tenant"

        }
    }

}