package com.utn.firstapp.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject



@Entity(tableName = "clubs")
    class Club (id:String, name: String,founded: String, country: String, nickname: String, imageurl: String, league: String, countryflag:String)
    {
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: String

        @ColumnInfo(name = "name")
        var name: String

        @ColumnInfo(name = "founded")
        var founded: String

        @ColumnInfo(name = "country")
        var country: String

        @ColumnInfo(name = "nickname")
        var nickname: String

        @ColumnInfo(name = "imageurl")
        var imageurl: String

        @ColumnInfo(name = "countryflag")
        var countryflag: String


        @ColumnInfo(name = "league")
        var league: String
        init {
            this.id = id
            this.name = name
            this.country = country
            this.founded = founded
            this.nickname = nickname
            this.imageurl = imageurl
            this.league = league
            this.countryflag = countryflag
        }


    }