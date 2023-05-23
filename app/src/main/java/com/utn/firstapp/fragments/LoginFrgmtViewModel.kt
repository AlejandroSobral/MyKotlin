package com.utn.firstapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.entities.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFrgmtViewModel @Inject constructor(
    val context: Context

) : ViewModel() {



    // Access a Cloud Firestore instance from your Activity
    val db_int = Firebase.firestore
    val TAGINT = "DBINT"
    var auxUser: User = User("", "User", "asd", "1", "asd") // Instance un User

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val gson: Gson = Gson()


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)


    private fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString("currentUser", userJson).apply()
    }



    fun getUserFromUsernameAndPassword(userString: String, passwordString: String) {

        //usertypeIn = userDao?.fetchUserByUserAndPass(inputTextuser, inputTextpass) as User

        db_int.collection("users")
            .whereEqualTo("name", userString)
            .whereEqualTo("password", passwordString)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        auxUser.id = document.getString("id") ?: ""
                        auxUser.password = document.getString("password") ?: ""
                        auxUser.lastName = document.getString("lastname") ?: ""
                        auxUser.email = document.getString("email") ?: ""
                        auxUser.name = document.getString("name") ?: ""
                        _user.value = auxUser

                        saveUser(auxUser)
                    }

                }

            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB connection documents: ", exception)

            }
    }
}






    //userList = userDao?.fetchAllUsers() as MutableList<User>



