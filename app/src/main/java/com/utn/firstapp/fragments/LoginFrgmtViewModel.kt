package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {


    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUserFromUsernameAndPassword(userString: String, passwordString: String) {
        val dbInt = Firebase.firestore
        var auxUser: User = User("", "User", "asd", "1", "asd") // Instance un User

        dbInt.collection("users")
            .whereEqualTo("name", userString)
            .whereEqualTo("password", passwordString)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // FIXME - Deberias cambiar la logica para traer solo un user, o para asegurarte de que hay un solo doc...
                    for (document in result) {
                        auxUser.id = document.getString("id") ?: ""
                        auxUser.password = document.getString("password") ?: ""
                        auxUser.lastname = document.getString("lastname") ?: ""
                        auxUser.email = document.getString("email") ?: ""
                        auxUser.name = document.getString("name") ?: ""
                        auxUser.lastposition = document.getString("lastposition") ?: ""
                        _user.value = auxUser

                        preferencesManager.saveCurrentUser(auxUser)
                    }

                }

            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB connection documents: ", exception)

            }
    }
}
