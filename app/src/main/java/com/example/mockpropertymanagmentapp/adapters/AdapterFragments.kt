package com.example.mockpropertymanagmentapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mockpropertymanagmentapp.fragments.RegisterLandlordFragment
import com.example.mockpropertymanagmentapp.fragments.RegisterPropertyManagerFragment
import com.example.mockpropertymanagmentapp.fragments.RegisterTenantFragment
import com.example.mockpropertymanagmentapp.fragments.RegisterVendorFragment

class AdapterFragments(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RegisterLandlordFragment()
            1 -> RegisterTenantFragment()
            2 -> RegisterPropertyManagerFragment()
            else -> RegisterVendorFragment()
        }
    }

    override fun getPageTitle(position: Int) : CharSequence? {
        return when(position){
            0 -> "Landlord"
            1 -> "Tenant"
            2 -> "Property Manager"
            else -> "Vendor"
        }
    }

}