package com.utn.firstapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.R
import com.utn.firstapp.entities.User
import com.google.gson.Gson
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state : MutableLiveData<State> = MutableLiveData()
    private val _teams = MutableLiveData<ClubRepository>()
    val dbInt = Firebase.firestore
    val teams: LiveData<ClubRepository>
        get() = _teams

    fun getUser(): User? {
        return preferencesManager.getCurrentUser()
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