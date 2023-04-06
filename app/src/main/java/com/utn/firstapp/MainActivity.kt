package com.utn.firstapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.utn.firstapp.entities.User

class MainActivity : AppCompatActivity() {



    var userList: MutableList<User> = mutableListOf()



    private lateinit var label: TextView


    var labelText: String = "Appearing test"
    var labelTextVoid: String = ""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}