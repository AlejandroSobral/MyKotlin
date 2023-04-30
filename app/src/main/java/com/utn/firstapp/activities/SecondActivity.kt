package com.utn.firstapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.firstapp.R

class SecondActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var navHomeFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)
        navHomeFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        bottomNav = findViewById(R.id.NavBottomBar)
        NavigationUI.setupWithNavController(bottomNav, navHomeFragment.navController)


    }
}