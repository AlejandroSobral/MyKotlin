package com.utn.firstapp.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
class Club (var name: String,var founded : String, var country: String, var nickname: String, var imageURL: String, var league: String, var id: Int): Parcelable{
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object : Parceler<Club> {
        override fun Club.write(p0: Parcel, p1: Int) {
            TODO("Not yet implemented")
        }

        override fun create(parcel: Parcel): Club = TODO()
    }

}
