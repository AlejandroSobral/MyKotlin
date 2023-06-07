package com.utn.firstapp.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RecuPassFrgmtViewModel : ViewModel() {

    val state = SingleLiveEvent<State>()

    fun mySendEmail(emailAdd: String) {
        state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO)
        {
            val ret = recPass(emailAdd)
            if(ret == "OK") {
                state.postValue(State.SUCCESS)
            }
            if(ret == "WRONG")
            {
                state.postValue(State.FAILURE)
            }
        }
    }

    private suspend fun recPass(emailAdd: String):String {

        return try {
            Firebase.auth.sendPasswordResetEmail(emailAdd).await()
            "OK"

        } catch (e: Exception) {
            "WRONG"

        }

    }
}




