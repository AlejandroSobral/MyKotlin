package com.utn.firstapp.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class User (id:String, name: String,lastName: String, email: String, password: String)
{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String

    @ColumnInfo(name = "name")
    var name: String

    @ColumnInfo(name = "email")
    var email: String

    @ColumnInfo(name = "lastName")
    var lastName: String

    @ColumnInfo(name = "password")
    var password: String

    init {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.lastName = lastName
    }


}




