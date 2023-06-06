package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditClubDetailViewModel : ViewModel() {

    val state = SingleLiveEvent<State>()
    var getTeam = SingleLiveEvent<Club?>()


    fun myUpdateClubCor(getClub: Club) {
        state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            var result: Club? = null
            result = updateFirestoreClub(getClub)

            if (result != null) {
                state.postValue(State.SUCCESS)

            } else {
                state.postValue(State.FAILURE)
            }
        }
    }

    suspend fun updateFirestoreClub(updateClub: Club): Club? {
        val dbInt = Firebase.firestore
        val collection = dbInt.collection("teams")
        val documentRef = collection.document(updateClub.id)

        return try {
            documentRef.set(updateClub).await()
            updateClub
        } catch (e: Exception) {
            null
        }
    }


    fun myGetClubFromID(clubID: String) {

        viewModelScope.launch(Dispatchers.IO)
        {
        firebaseGetClubFromID(clubID)
        }
    }

    private suspend fun firebaseGetClubFromID(clubID: String) {

        val dbInt = Firebase.firestore

        try {
            val result = dbInt.collection("teams").whereEqualTo("id", clubID).limit(1).get().await()

            var auxClub: Club = Club("", "", "", "", "", "", "", "") // Instance un User
            if (!result.isEmpty) {
                for (document in result) {
                    auxClub.name = document.getString("name") ?: ""
                    auxClub.country = document.getString("country") ?: ""
                    auxClub.founded = document.getString("founded") ?: ""
                    auxClub.league = document.getString("league") ?: ""
                    auxClub.nickname = document.getString("nickname") ?: ""
                    auxClub.countryflag = document.getString("countryflag") ?: ""
                    auxClub.id = document.getString("id") ?: ""
                    auxClub.imageurl = document.getString("imageurl") ?: ""



                }
                getTeam.postValue(auxClub)
                Log.d("TestDB", "ClubDetail is OK. TeamID -> $auxClub.id")
            }
        }
        catch(e:Exception){
            Log.d("TestDB", "ClubDetail exception")

        }
    }

}

