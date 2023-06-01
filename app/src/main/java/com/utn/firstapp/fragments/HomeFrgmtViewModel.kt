package com.utn.firstapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.R
import com.utn.firstapp.entities.User
import com.google.gson.Gson
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    private val _teams = MutableLiveData<ClubRepository>()
    val dbInt = Firebase.firestore
    val teams: LiveData<ClubRepository>
        get() = _teams

    fun getUserfromPref(): User? {
        return preferencesManager.getCurrentUser()
        state.postValue(State.SUCCESS)
    }

    fun updateUserPref(user:User) {
        preferencesManager.saveCurrentUser(user)
        state.postValue(State.SUCCESS)
    }

    fun updateUser(user: User)
    {
        state.postValue(State.LOADING)
        preferencesManager.saveCurrentUser(user)
        var auxUser = preferencesManager.getCurrentUser()
        val usersCollection = dbInt.collection("users")
        if (auxUser != null) {
            user.id = auxUser.id
        }
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


    fun updateUserInFirestore(updateUser: User) {

            state.postValue(State.LOADING)
            preferencesManager.saveCurrentUser(updateUser)
            var auxUser = preferencesManager.getCurrentUser()
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            currentUser?.let { firebaseUser ->
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(updateUser.name)
                    //.setPhotoUri(Updateuser.photoUrl)
                    .build()

                // Create a HashMap to store the "position" parameter
                val positionMap = hashMapOf("position" to updateUser.lastposition)

                // Update the user's profile and custom claims in parallel
                firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            // User profile update successful
                            println("User profile updated successfully: ${firebaseUser.uid}")

                            // Update the "position" field in Firestore user document
                            val db = FirebaseFirestore.getInstance()
                            val userRef = db.collection("users").document(firebaseUser.uid)
                            userRef.update(positionMap as Map<String, Any>)
                                .addOnSuccessListener {
                                    // "position" field update successful
                                    println("Position updated in Firestore: ${firebaseUser.uid}")
                                    state.postValue(State.SUCCESS)
                                }
                                .addOnFailureListener { positionException ->
                                    // Error occurred while updating "position" field
                                    println("Failed to update position in Firestore: ${positionException.message}")
                                }
                        } else {
                            // User profile update failed
                            state.postValue(State.LOADING)
                            val profileException = profileTask.exception
                            // Handle the error
                            println("Failed to update user profile: ${profileException?.message}")
                        }
                    }
            }
        }

    fun getClubsFromDB(): MutableList<Club> {
        val dbInt = Firebase.firestore
        var clubRepo: ClubRepository = ClubRepository()
        var auxClub: Club = Club("", "", "", "", "", "", "", "")

        dbInt.collection("teams")
            .orderBy("name")
            .get()
            .addOnSuccessListener { result ->

                if (!result.isEmpty) {
                    for (document in result) {
                        var auxClub: Club = Club("", "", "", "", "", "", "", "")
                        //Log.d("TestDB", "${document.id} => ${document.data}")
                        auxClub.id = document.id
                        auxClub.name = document.getString("name") ?: ""
                        auxClub.country = document.getString("country") ?: ""
                        auxClub.founded = document.getString("founded") ?: ""
                        auxClub.league = document.getString("league") ?: ""
                        auxClub.nickname = document.getString("nickname") ?: ""
                        auxClub.countryflag = document.getString("countryflag") ?: ""
                        auxClub.imageurl = document.getString("imageurl") ?: ""

                        clubRepo.clubList.add(auxClub)
                    }
                    _teams.postValue(clubRepo)

                }
                Log.d("TestDB", "SuccessListener and finish adding list")
            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB getting teams connection documents: ", exception)

            }

        return clubRepo.clubList
    }
}