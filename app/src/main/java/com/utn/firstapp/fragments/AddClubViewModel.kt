package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject

class AddClubViewModel : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData()



    fun myAddClub(newClub: Club) {
        state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            var result: Club? = null
            result = addFirestoreClub(newClub)

            if (result != null) {
                state.postValue(State.SUCCESS)

            } else {
                state.postValue(State.FAILURE)
            }
        }
    }

    suspend fun addFirestoreClub(newClub: Club): Club? {
        val dbInt = Firebase.firestore
        val collection = dbInt.collection("teams")

        return try {
            val documentRef = collection.add(newClub).await()
            newClub.id = documentRef.id
            dbInt.collection("teams").document(newClub.id).update("id", newClub.id).await()
            newClub
        } catch (e: Exception) {
            null
        }



    }

}