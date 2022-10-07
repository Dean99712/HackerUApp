package com.example.hackeruapp.model

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.hackeruapp.R

class SharedPreferencesManager private constructor(context: Context) {

    val sharedPreferences =
        context.getSharedPreferences(R.string.app_name.toString(), AppCompatActivity.MODE_PRIVATE)

//    fun setUser(email: String,firstName: String,lastName: String) {
//        sharedPreferences.edit().putString("USER_EMAIL", email).apply()
//        sharedPreferences.edit().putString("USER_FIRST_NAME", firstName).apply()
//        sharedPreferences.edit().putString("USER_LAST_NAME", lastName).apply()
//    }
//
//    fun getEmail(): String {
//        return sharedPreferences.getString("USER_EMAIL", "")!!
//    }

    companion object {

        lateinit var myUser: User

        private lateinit var instance: SharedPreferencesManager

        fun getInstance(context: Context): SharedPreferencesManager {
            if (!Companion::instance.isInitialized) {
            }
            instance = SharedPreferencesManager(context)
            return instance
        }
    }
}