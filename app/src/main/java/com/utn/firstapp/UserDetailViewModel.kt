package com.utn.firstapp

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val dbInt = Firebase.firestore

    fun getUser(): User {
        val gson: Gson = Gson()
        val userJson = sharedPreferences.getString("currentUser", null)
        return gson.fromJson(userJson, User::class.java)
    }

    fun updateUser(user: User)
    {

        val gson: Gson = Gson()
        val userJson = sharedPreferences.getString("currentUser", null)
        val pureJsonUser = JSONObject(userJson)
        val usersCollection = dbInt.collection("users")
        user.id = pureJsonUser.getString("id")
        usersCollection.document(pureJsonUser.getString("id"))
            .set(user)
            .addOnSuccessListener {
                Log.d("UpdateUser", "OK")
            }
            .addOnFailureListener { exception ->
                Log.d("UpdateUser", "FAILED")
            }
    }


}