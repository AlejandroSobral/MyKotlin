package com.utn.firstapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.utn.firstapp.R


class SplashActiviy : AppCompatActivity() {

    var imgHomeLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"
    lateinit var imgSplashLogo: ImageView
    lateinit var v: View

    private val SPLASH_TIME_OUT: Long = 1000 // 1 SEG
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activiy)
        Handler().postDelayed(
            {
            startActivity(Intent(this, MainActivity::class.java))
                finish()
        }, SPLASH_TIME_OUT)

        imgSplashLogo = findViewById(R.id.imgSplashLogo)
        Glide.with(this).load(imgHomeLogoURL).into(imgSplashLogo)

    }
}