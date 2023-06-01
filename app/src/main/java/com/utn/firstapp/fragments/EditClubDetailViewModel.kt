package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.State

class EditClubDetailViewModel : ViewModel() {

    val state = SingleLiveEvent<State>()
    private val _detailClub = MutableLiveData<Club>()
    val team: LiveData<Club>
        get() = _detailClub

    fun updateClub(getClub: Club)
    {
        state.postValue(State.LOADING)
        val dbInt = Firebase.firestore
        val teamsCollection = dbInt.collection("teams")
        teamsCollection.document(getClub.id)
            .set(getClub)
            .addOnSuccessListener {
                Log.d("UpdateClub", "OK")
                state.postValue(State.SUCCESS)
            }
            .addOnFailureListener { exception ->
                Log.d("UpdateClub", "FAILED")
                state.postValue(State.FAILURE)
            }
    }



    fun getClubFromID(clubID: String) {
        val dbInt = Firebase.firestore
        var auxClub: Club = Club("", "", "", "", "", "", "", "") // Instance un User

        dbInt.collection("teams")
            .whereEqualTo("id", clubID)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // FIXME - Deberias cambiar la logica para traer solo un user, o para asegurarte de que hay un solo doc...
                    for (document in result) {

                        auxClub.name = document.getString("name") ?: ""
                        auxClub.country = document.getString("country") ?: ""
                        auxClub.founded = document.getString("founded") ?: ""
                        auxClub.league = document.getString("league") ?: ""
                        auxClub.nickname = document.getString("nickname") ?: ""
                        auxClub.countryflag = document.getString("countryflag") ?: ""
                        auxClub.id = document.getString("id") ?: ""
                        auxClub.imageurl = document.getString("imageurl") ?: ""

                        _detailClub.value = auxClub

                    }
                    _detailClub.postValue(auxClub)
                }
                Log.d("TestDB", "ClubDetail is over: ")
            }
            .addOnFailureListener { exception ->
                Log.d("TestDB", "Error DB connection Club Detail: ", exception)

            }
    }

}

