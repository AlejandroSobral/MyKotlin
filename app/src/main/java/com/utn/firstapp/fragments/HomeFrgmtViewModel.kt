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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {


    private val _teams = MutableLiveData<ClubRepository>()
    val teams: LiveData<ClubRepository>
        get() = _teams

    fun getClubsFromDB(): MutableList<Club> {
        val dbInt = Firebase.firestore
        var clubRepo: ClubRepository = ClubRepository()
        var auxClub: Club = Club("", "", "", "", "", "", "", "")


        dbInt.collection("teams")
            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {
                    for (document in result) {

                        var auxClub: Club = Club("", "", "", "", "", "", "", "")
                        //Log.d("TestDB", "${document.id} => ${document.data}")
                        auxClub.id = document.id
                        auxClub.name = document.getString("Club") ?: ""
                        auxClub.country = document.getString("Country") ?: ""
                        auxClub.founded = document.getString("Founded") ?: ""
                        auxClub.league = document.getString("League") ?: ""
                        auxClub.nickname = document.getString("Nick") ?: ""
                        auxClub.countryFlag = document.getString("countryFlag") ?: ""
                        auxClub.imageURL = document.getString("imageURL") ?: ""

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