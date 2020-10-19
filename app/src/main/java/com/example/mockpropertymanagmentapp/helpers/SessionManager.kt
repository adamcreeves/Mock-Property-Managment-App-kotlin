package com.example.mockpropertymanagmentapp.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat.KEY_TOKEN

class SessionManager(private var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS"
    private val KEY_TOKEN = "token"
    private val KEY_USERID = "userId"
    private val KEY_IMAGE = "image"
    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserLogin(token: String, userId: String) {
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_USERID, userId)
        editor.commit()
    }

    fun getUserId(): String {
        var userId = sharedPreferences.getString(KEY_USERID, null)
        return userId.toString()
    }

    fun getQuickLogin(): Boolean {
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token != null
    }

    fun saveImageUrl(image: String) {
        editor.putString(KEY_IMAGE, image)
        editor.commit()
    }

    fun getImageUrl(): String {
        return sharedPreferences.getString(KEY_IMAGE, null).toString()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    companion object {
        @SuppressLint("RestrictedApi")
        const val TOKEN = KEY_TOKEN
    }
}
