package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.utn.firstapp.fragments.LoginFrgmtViewModel
import kotlinx.coroutines.tasks.await

@HiltViewModel
class UserSignupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()


    fun userSignUpCor(email: String, password: String, confPassword: String): FirebaseUser? {
        state.postValue(State.LOADING)

        if (password.length < 6 || confPassword.length < 6) {
            state.postValue(State.PASSLENGTH)
            return null
        }

        if (password != confPassword) {
            state.postValue(State.PASSNOTEQUAL)
            return null
        }

        var user: FirebaseUser?
        var result: FirebaseUser? = null

        //Check if user exists
        try {
            viewModelScope.launch(Dispatchers.IO) {
                user = getAuthFromFirestoneCour(email, password)
                if (user == null) {
                    result = insertUserAuthCor(email, password)
                    if (result != null) { //Save user into the Shared Preference
                        val auxUser = User(result!!.uid, result!!.email.toString(), "",  "0")
                        preferencesManager.saveCurrentUser(auxUser)
                        state.postValue(State.SUCCESS)
                    }
                } else {
                    state.postValue(State.USEREXISTS)
                }
            }
        } catch (e: Exception) {
            state.postValue(State.FAILURE)
            Log.d("getAuthFrom", "Raised Exception")
        }
        return result

    }


    private suspend fun getAuthFromFirestoneCour(email: String, password: String): FirebaseUser? {

        return try {
            var result: FirebaseUser? = null
            var auth: FirebaseAuth = Firebase.auth
            result = (auth.signInWithEmailAndPassword(email, password).await()).user

            if (result != null) { //Save user into the Shared Preference
                val auxUser = User(result.uid, result.email.toString(), "", "0")
                preferencesManager.saveCurrentUser(auxUser)
            }
            result
        } catch (e: Exception) {
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }

    private suspend fun insertUserAuthCor(username: String, password: String): FirebaseUser? {

        return try {
            lateinit var result: AuthResult
            var auth: FirebaseAuth = Firebase.auth
            result = auth.createUserWithEmailAndPassword(username, password).await()

            if (result != null) {
                return result.user
            }
            result.user
        } catch (e: Exception) {
            Log.d("insertUserAuthCor", "Raised Exception")
            return null
        }


    }

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
                        //.setDisplayName(insertUser.name)
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

    private fun insertUserLocal(insertUser: User) {
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

        var auxUser: User = User("", "", "", "")

        dbInt.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {
                    for (document in result) {

                        //Log.d("TestDB", "${document.id} => ${document.data}")
                        auxUser.id = document.id
                        //auxUser.name = document.getString("name") ?: ""
                        auxUser.password = document.getString("password") ?: ""
                        auxUser.email = document.getString("email") ?: ""
                        //auxUser.lastname = document.getString("lastname") ?: ""

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
