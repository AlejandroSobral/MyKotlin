package com.utn.firstapp

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    var profilePic = SingleLiveEvent<ByteArray?>()

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
        } catch (e: Exception) {
            Log.d("UpdatePassFirebaseUser", "CATCH - Error updating password:")
            return null
        }
    }



    fun myUploadProfilePicture() {

        viewModelScope.launch(Dispatchers.IO)
        {
            val picture = UploadFile()



        }

    }

    suspend fun UploadFile() {
        val currentUser = preferencesManager.getCurrentUser()
        val storage = Firebase.storage

        var storageRef = storage.reference
        val path = "users/profPictures" + (currentUser?.id ?: 0)

            var imageRef: StorageReference? = storageRef.child(path)

            var file = Uri.fromFile(File("path/to/images/rivers.jpg"))
            val riversRef = storageRef.child("images/${file.lastPathSegment}")
            try {
                val taskSnap = riversRef.putFile(file).await()
            } catch (e: java.lang.Exception) {
                Log.d("Storage", "UploadFile raise exception")
            }

    }

    fun myGetUserProfilePicCor()
    {
        val currentUser = preferencesManager.getCurrentUser()
        viewModelScope.launch(Dispatchers.IO)
        {
            val profilePicture = getProfilePic()
            profilePic.postValue(profilePicture)



        }
    }

    suspend fun getProfilePic() : ByteArray? {

        try {
            val storage = Firebase.storage
            var storageRef = storage.reference
            val currentUser = preferencesManager.getCurrentUser()
            val path = "users/profPictures/" + (currentUser?.id ?: 0) + "/profilepic.png"
            Log.d("Storage", "$path")
            var islandRef = storageRef.child(path)

            val ONE_MEGABYTE: Long = 1024 * 1024
            val pic = islandRef.getBytes(ONE_MEGABYTE).await()
            return pic
        }
        catch(e:Exception)
        {
            val pic : ByteArray? = null
            return pic
        }


    }

}
