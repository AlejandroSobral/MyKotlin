package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
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


    private val _user = MutableLiveData<FirebaseUser?>()
    val user: MutableLiveData<FirebaseUser?>
        get() = _user

    /*fun getUserFromUsernameAndPassword(userString: String, passwordString: String) {
        val dbInt = Firebase.firestore
        var auxUser: User = User("", "", "", "", "") // Instance un User

        dbInt.collection("users")
            .whereEqualTo("name", userString)
            .whereEqualTo("password", passwordString)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
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

                if(result.isEmpty){
                    _user.value = null
                }

            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB connection documents: ", exception)

            }
    }*/

    fun getAuthFromFirestone(email:String, password:String) {

        var auth: FirebaseAuth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User authentication success
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        // User is signed in
                        val userId = firebaseUser.uid
                        println("User signed in with UID: $userId")
                        _user.value = firebaseUser

                    }
                } else {
                    // User authentication failed
                    _user.value = null
                    val exception = task.exception
                    // Handle the error
                    println("Authentication failed: ${exception?.message}")
                }
            }
    }
}
