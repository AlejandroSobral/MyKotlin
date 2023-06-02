package com.utn.firstapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val dbInt = Firebase.firestore

    fun getUser(): User? {
        return preferencesManager.getCurrentUser()
        state.postValue(State.SUCCESS)
    }



    fun myUpdatePassFirebaseUser(
        user: String,
        oldPassword: String,
        newPassword: String,
        PasswordCheck: String
    ) {

        state.setValue(State.LOADING)
        if (newPassword != PasswordCheck) {
            state.postValue(State.PASSNOTEQUAL)
           return
        }
        if (newPassword.length < 6) {
            state.postValue(State.PASSLENGTH)
            return
        }
        viewModelScope.launch(Dispatchers.IO)
        {
            val result = firebasePassUpd(user, oldPassword, newPassword)
            if (result != null) {
                state.postValue(State.SUCCESS)
            } else state.postValue(State.FAILURE)
        }
    }

    private suspend fun firebasePassUpd(
        email: String,
        oldPass: String,
        newPassword: String
    ): AuthResult? {
        try {
            val auth = Firebase.auth
            // Reauthenticate the user with their old password.
            val signInResult = auth.signInWithEmailAndPassword(email, oldPass).await()
            if (signInResult != null) {
                // Update the user's password.
                auth.currentUser!!.updatePassword(newPassword).await()
                return signInResult

                Log.d("UpdatePassFirebaseUser", "Password updated successfully!")
            } else {
                Log.d("UpdatePassFirebaseUser", "Error updating password:")
                return null
            }
        }
        catch(e: Exception)
        { Log.d("UpdatePassFirebaseUser", "CATCH - Error updating password:")
            return null}
    }

}
