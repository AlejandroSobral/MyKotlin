package com.utn.firstapp

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val dbInt = Firebase.firestore

    fun getUser(): User? {
        return preferencesManager.getCurrentUser()
        state.postValue(State.SUCCESS)
    }

    fun updateUser(user: User)
    {
        state.postValue(State.LOADING)
        preferencesManager.saveCurrentUser(user)
        val usersCollection = dbInt.collection("users")

        usersCollection.document(user.id)
            .set(user)
            .addOnSuccessListener {
                Log.d("UpdateUser", "OK")
                state.postValue(State.SUCCESS)
            }
            .addOnFailureListener { exception ->
                Log.d("UpdateUser", "FAILED")
                state.postValue(State.FAILURE)
            }
    }


}
