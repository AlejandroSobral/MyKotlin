package com.utn.firstapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class HomeFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    var teams = SingleLiveEvent<ClubRepository?>()

    val dbInt = Firebase.firestore


    fun getUserfromPref(): User? {
        return preferencesManager.getCurrentUser()
        state.postValue(State.SUCCESS)
    }


    fun updateUserPref(user: User) {
        preferencesManager.saveCurrentUser(user)
        state.postValue(State.SUCCESS)
    }




    fun mygetClubsFromDBCor() {
        state.postValue(State.LOADING)
        var result: ClubRepository? = null
        try {
            viewModelScope.launch(Dispatchers.IO) {

                result = getClubsCor()


                if (result != null) {
                    //saveDataClubShPr(result!!)
                    state.postValue(State.SUCCESS)
                    teams.postValue(result!!)

                }
                if (result == null) {
                    state.postValue(State.FAILURE)
                }
            }

        }
        catch(e:Exception)
        {
        }

    }

    private suspend fun getClubsCor(): ClubRepository? {
        val dbInt = Firebase.firestore
        var clubRepo: ClubRepository = ClubRepository()

        try {
            var result = dbInt.collection("teams").orderBy("name").get().await()
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
            return clubRepo
        } catch (e: Exception) {
            return null
        }

    }
}