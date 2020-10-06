package com.example.mockpropertymanagmentapp.helpers

import android.content.Context

class SessionManager(private var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS"
    private val KEY_TOKEN = "token"
    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserLogin(token: String) {
        editor.putString(KEY_TOKEN, token)
        editor.commit()
    }

    fun getQuickLogin() : Boolean {
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token !=null
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

}
