package com.example.mockpropertymanagmentapp.ui.properties.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Property
import kotlinx.android.synthetic.main.row_adapter_properties.view.*

class AdapterProperties(var myContext: Context, var myList: ArrayList<Property>) :
    RecyclerView.Adapter<AdapterProperties.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =
            LayoutInflater.from(myContext).inflate(R.layout.row_adapter_properties, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var property = myList[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(it: ArrayList<Property>) {
        notifyDataSetChanged()
        myList = it
    }

    inner class myViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(property: Property) {
            itemView.text_view_address.text = property.address
            itemView.text_view_city.text = property.city
            itemView.text_view_state.text = property.state
            itemView.text_view_country.text = property.country
            itemView.text_view_purchase_price.text = property.purchasePrice
        }
    }
}