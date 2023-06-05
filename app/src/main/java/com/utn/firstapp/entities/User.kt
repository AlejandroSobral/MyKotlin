package com.utn.firstapp.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class User (id:String, email: String, password: String, lastposition: String = "0")
{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String

    @ColumnInfo(name = "email")
    var email: String

    @ColumnInfo(name = "password")
    var password: String

    @ColumnInfo(name = "lastposition")
    var lastposition: String



    init {
        this.id = id
        this.email = email
        this.password = password
        this.lastposition = lastposition
    }


}




