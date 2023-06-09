package com.utn.firstapp

import android.content.SharedPreferences
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.utn.firstapp.entities.User
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository

class PreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {



    fun saveNamedLoc(saveNamedLoc: String) {
        val uJson = gson.toJson(saveNamedLoc)
        sharedPreferences.edit().putString("NamedLoc", uJson).apply()
    }

    fun getNamedLoc() :String {
        val uJson = sharedPreferences.getString("NamedLoc", null)
        return gson.fromJson(uJson, String::class.java)
    }

    fun saveLocation(location: String) {
        val uJson = gson.toJson(location)
        sharedPreferences.edit().putString("GPSLoc", uJson).apply()
    }

    fun getLocation(): String {
        val uJson = sharedPreferences.getString("GPSLoc", null)
        return gson.fromJson(uJson, String::class.java)
    }

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

fun saveCurrentClubList(clubList: ClubRepository)
{
    val clubsJson = gson.toJson(clubList)
    sharedPreferences.edit().putString("currentClubList", clubsJson).apply()
}

    fun getCurrentClubList(): ClubRepository?
    {
        val clubsJson = sharedPreferences.getString("currentClubList", null)
        return if (clubsJson != null) {
            gson.fromJson(clubsJson, ClubRepository::class.java)
        } else {
            return null
        }
    }



}