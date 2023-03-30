package com.utn.firstapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var label: TextView
    private lateinit var btnShow: Button
    private lateinit var btnHide: Button
    private lateinit var btnRed: Button
    private lateinit var btnBlue: Button

    var labelText: String = "Appearing test"
    var labelTextVoid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        label = findViewById(R.id.MyFirstTextView)
        btnHide = findViewById(R.id.btnHide)
        btnShow = findViewById(R.id.btnShow)
        btnRed = findViewById(R.id.btnRed)
        btnBlue = findViewById(R.id.btnBlue)



        btnShow.setOnClickListener {
            label.text = labelText
        }

        btnHide.setOnClickListener {
            label.text = labelTextVoid
        }

        btnRed.setOnClickListener {
            label.setTextColor(Color.parseColor("#0afd3f"))
        }

        btnBlue.setOnClickListener {
            label.setTextColor(Color.parseColor("#000d3f"))
        }

    }
}