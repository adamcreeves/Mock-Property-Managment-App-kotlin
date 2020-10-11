package com.example.mockpropertymanagmentapp.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.ui.auth.activities.LoginActivity
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.data.models.RegisterResponse
import com.example.mockpropertymanagmentapp.data.models.Tenant
import com.example.mockpropertymanagmentapp.databinding.FragmentRegisterLandlordBinding
import com.example.mockpropertymanagmentapp.databinding.FragmentRegisterTenantBinding
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.auth.AuthListener
import com.example.mockpropertymanagmentapp.ui.auth.AuthViewModel
import kotlinx.android.synthetic.main.fragment_register_tenant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterTenantFragment : Fragment(), AuthListener {
    lateinit var binding: FragmentRegisterTenantBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.setContentView(activity!!, R.layout.fragment_register_tenant)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.authListener = this
        return binding.root
    }

    private fun init(view: View) {
        var api = MyApi()
        view.button_register_tenant_submit.setOnClickListener {
            var landlordEmail = view.edit_text_register_tenant_landlord_email.text.toString()
            var email = view.edit_text_register_tenant_email.text.toString()
            var name = view.edit_text_register_tenant_name.text.toString()
            var password = view.edit_text_register_tenant_password.text.toString()
            var confirmPassword = view.edit_text_register_tenant_confirm_password.text.toString()
            var type = "tenant"
            if (password == confirmPassword && password.length > 5) {
                var tenant = Tenant(email, landlordEmail, name, password, type)
                api.registerTenant(tenant)
                    .enqueue(object: Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show()
                            context!!.startActivity(Intent(context, LoginActivity::class.java))
                        }
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(activity, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else Toast.makeText(
                activity,
                "Passwords don't match or less than 6 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
        view.button_register_tenant_to_login.setOnClickListener{
            context!!.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onStarted() {
        binding.root.context.toastShort("Registration initiated")
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            binding.root.context.toastShort("Registration successful")
            binding.root.context.startActivity(Intent(context, LoginActivity::class.java))
        })
    }

    override fun onFailure(message: String) {
        binding.root.context.toastShort(message)
    }
}


