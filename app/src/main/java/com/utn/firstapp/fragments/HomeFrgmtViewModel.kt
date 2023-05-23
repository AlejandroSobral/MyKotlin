package com.utn.firstapp.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.utn.firstapp.R
import com.utn.firstapp.entities.User
import com.google.gson.Gson



class HomeFrgmtViewModel : ViewModel() {

    private lateinit var context: Context
    private val gson: Gson = Gson()


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    fun getUser(): User? {
        val userJson = sharedPreferences.getString("currentUser", null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

}