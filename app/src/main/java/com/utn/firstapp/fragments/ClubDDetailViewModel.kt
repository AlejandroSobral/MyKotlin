package com.utn.firstapp.fragments

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ClubDDetailViewModel : ViewModel() {

    val state = SingleLiveEvent<State>()
    private val _detailClub = MutableLiveData<Club?>()
    val team: MutableLiveData<Club?>
        get() = _detailClub



    fun getClubFromID(clubID: String) {
        val dbInt = Firebase.firestore
        var auxClub: Club = Club("", "", "", "", "", "", "", "") // Instance un User

        state.postValue(State.LOADING)
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
                _detailClub.value = null

            }
    }

    fun deleteClubFromID (clubID: String) {
        val dbInt = Firebase.firestore
        state.postValue(State.LOADING)
        dbInt.collection("teams").document(clubID)
            .delete()
            .addOnSuccessListener {
                Log.d("TestDB", "Club has been deleted.")
                state.postValue(State.SUCCESS)
                    }

            .addOnFailureListener { exception ->
                Log.d("TestDB", "Club deleting has failed: ", exception)

            }
    }

    fun myDeleteClubCor(clubID: String) {
        state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            var result: String? = null
            result = deleteFirestoreClub(clubID)

            if (result != null) {
                state.postValue(State.SUCCESS)

            } else {
                state.postValue(State.FAILURE)
            }
        }
    }

    suspend fun deleteFirestoreClub(clubID: String): String? {
        val dbInt = Firebase.firestore
        val collection = dbInt.collection("teams")
        val documentRef = collection.document(clubID)

        return try {
            documentRef.delete().await()
            clubID
        } catch (e: Exception) {
            null
        }
    }
}

