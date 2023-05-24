package com.utn.firstapp

import android.content.SharedPreferences
import com.google.gson.Gson
import com.utn.firstapp.entities.User

class PreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    fun getCurrentUser(): User? {
        val userJson = sharedPreferences.getString("currentUser", null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            return null
        }
        //return User("", "", "", "", "")
    }

    fun saveCurrentUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString("currentUser", userJson).apply()
    }


}