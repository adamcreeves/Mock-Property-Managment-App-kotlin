package com.example.mockpropertymanagmentapp.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun Context.debug(message: String) {
    Log.d("abc", message)
}