package com.utn.firstapp.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.utn.firstapp.R
import com.utn.firstapp.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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