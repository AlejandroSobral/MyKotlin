package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSignupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun insertUserAuth(insertUser: User) {

        var auth: FirebaseAuth = Firebase.auth

        auth.createUserWithEmailAndPassword(insertUser.email, insertUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FirestoreAuth", "createUserWithEmail:success")
                    val user = auth.currentUser

                    val userId = user?.uid
                    if (userId != null) {
                        // Use the UID as needed
                        insertUser.id = userId
                        insertUserLocal(insertUser)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FirestoreAuth", "createUserWithEmail:failure", task.exception)

                }
            }

    }

    fun insertUserAuthv2(insertUser: User) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(insertUser.email, insertUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                        .setDisplayName(insertUser.name)
                        //.setPhotoUri(photoUri)
                        .build()

                    user?.updateProfile(userProfileChangeRequest)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {

                                preferencesManager.saveCurrentUser(insertUser)
                            } else {
                                // Profile update failed
                                val exception = profileUpdateTask.exception
                                // Handle the error
                                println("Failed to update user profile: ${exception?.message}")
                            }
                        }
                } else {
                    // User creation failed
                    val exception = task.exception
                    // Handle the error
                    println("User creation failed: ${exception?.message}")
                }
            }
    }


    fun insertUserLocal(insertUser: User) {
        insertUser.lastposition = "0"
        val dbInt = Firebase.firestore
        dbInt.collection("users")
            .add(insertUser)
            .addOnSuccessListener { documentReference ->

                Log.d("TestDB", "DocumentSnapshot added with ID: ${documentReference.id}")

                val userId = documentReference.id
                insertUser.id = userId
                dbInt.collection("users").document(userId)
                    .update("id", userId)
                    .addOnSuccessListener {
                        Log.d("TestDB", "DocumentSnapshot successfully updated!")

                        preferencesManager.saveCurrentUser(insertUser)
                    }
                    .addOnFailureListener { e ->
                        Log.w("TestDB", "Error updating document", e)

                    }
            }
    }



    fun getUserFromNameAndPass(email: String, password: String): User {
        val dbInt = Firebase.firestore

        var auxUser: User = User("", "", "", "", "")

        dbInt.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {
                    for (document in result) {

                        //Log.d("TestDB", "${document.id} => ${document.data}")
                        auxUser.id = document.id
                        auxUser.name = document.getString("name") ?: ""
                        auxUser.password = document.getString("password") ?: ""
                        auxUser.email = document.getString("email") ?: ""
                        auxUser.lastname = document.getString("lastname") ?: ""

                    }

                }
                Log.d("TestDB", "SuccessListener and finish adding list")
            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB getting teams connection documents: ", exception)

            }
        return auxUser

    }


}
