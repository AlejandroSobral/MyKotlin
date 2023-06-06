package com.utn.firstapp.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.PreferencesManager
import com.utn.firstapp.SingleLiveEvent
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    private val _user = MutableLiveData<FirebaseUser?>()

    val user: MutableLiveData<FirebaseUser?>
        get() = _user


    suspend fun getAuthFromFirestoneCour(email: String, password: String): FirebaseUser? {

        return try {
            var result: FirebaseUser? = null
            var auth: FirebaseAuth = Firebase.auth
            result = (auth.signInWithEmailAndPassword(email, password).await()).user

            if (result != null) { //Save user into the Shared Preference
                val auxUser = User(result.uid,  result.email.toString(), "", "0")
                preferencesManager.saveCurrentUser(auxUser)

            }
            result
        } catch (e: Exception) {
            Log.d("getAuthFrom", "Raised Exception")
            null
        }
    }


    fun myFirebaseLogin(email: String, password: String): FirebaseUser? {
        state.postValue(State.LOADING)
        return try {
            var result: FirebaseUser? = null
            viewModelScope.launch(Dispatchers.IO) {
                result = getAuthFromFirestoneCour(email, password)
                Log.d("myFirebaseLogin - Resultado", "$result")
                if (result != null) {
                    state.postValue(State.SUCCESS)
                } else {
                    state.postValue(State.FAILURE)
                }
            }
            result
        } catch (e: Exception) {
            Log.d("myFireBaseLogin", "A ver $e")
            state.postValue(State.FAILURE)
            null
        }
    }
}
