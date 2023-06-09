package com.utn.firstapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
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
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeFrgmtViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val state = SingleLiveEvent<State>()
    var teams = SingleLiveEvent<ClubRepository?>()
    val stateLocation = SingleLiveEvent<State>()

    val dbInt = Firebase.firestore


    fun getNamedLocation() :String {
        return preferencesManager.getNamedLoc()
    }

    fun getUserfromPref(): User? {
        return preferencesManager.getCurrentUser()
        state.postValue(State.SUCCESS)
    }


    fun updateUserPref(user: User) {
        preferencesManager.saveCurrentUser(user)
        state.postValue(State.SUCCESS)
    }




    fun mygetClubsFromDBCorWithFilter() {
        state.postValue(State.LOADING)
        var result: ClubRepository? = null
        val namedLocation = preferencesManager.getNamedLoc()
        try {
            viewModelScope.launch(Dispatchers.IO) {

                result = getClubsCor()
                Log.d("Filtering", "namedLocation $namedLocation")
                val newList = result?.clubList?.filter { it.country == namedLocation } as MutableList<Club>
                Log.d("Filtering", "NewList $newList")

                result!!.clubList = newList


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

    private fun getCityCountryFromCoor(geocoder: Geocoder, lat: Double, long: Double): String {
        var CountryCityString = "Unknown"

        try {
            val direcciones: List<Address> = geocoder.getFromLocation(lat, long, 1) as List<Address>

            if (direcciones.isNotEmpty()) {
                val direccion: Address = direcciones[0]
                val country = direccion.countryName
                val city = direccion.locality

                if (city != null && country != null) {
                    //CountryCityString = "$city, $country"
                    CountryCityString = "$country"
                } else if (country != null) {
                    CountryCityString = country
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return CountryCityString
    }

    @SuppressLint("MissingPermission") // Permission has been checked previously, add perm check requires Context... which I'll not inject.
    fun getLocation(fusedLocationClient: FusedLocationProviderClient, geocoder: Geocoder) {
        stateLocation.postValue(State.LOADING)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val location = fusedLocationClient.lastLocation.await()
                    if (location != null) {
                        val namedLoc =
                            getCityCountryFromCoor(geocoder, location.latitude, location.longitude)
                        //Log.d("Loc", "RESULT: $namedLoc")
                        //Log.d("Loc", "RESULT LOC: $location")
                        preferencesManager.saveNamedLoc(namedLoc)
                        preferencesManager.saveLocation(location.toString())
                        stateLocation.postValue(State.SUCCESS)
                    }
                }
                catch(e:Exception){
                        // Handles errors
                        Log.e("Loc", "Error at getting GeoLocation")
                        stateLocation.postValue(State.FAILURE)
                }
            }
        }catch (e: Exception){
            stateLocation.postValue(State.FAILURE)
        }
    }

    fun getFlagByLocation():String?{
        val country = preferencesManager.getNamedLoc()
        if(country == "Argentina") {
            return "https://www.promiedos.com.ar/images/paises/ar.png"
        }
        else return null
    }

}