package com.example.mockpropertymanagmentapp.data.models

data class PropertiesResponse(
    val `data`: List<Property>,
    val error: Boolean
)

data class Property(
    val __v: Int? = null,
    val _id: String? = null,
    val address: String? = null,
    val city: String? = null,
    val country: String? = null,
    val image: String? = null,
    val mortageInfo: Boolean? = null,
    val propertyStatus: Boolean? = null,
    val purchasePrice: String? = null,
    val state: String? = null
)