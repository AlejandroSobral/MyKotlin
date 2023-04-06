package com.utn.firstapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.utn.firstapp.R

class Screen2Fragment : Fragment() {

    lateinit var label: TextView
    lateinit var btnNavigate: Button
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen2, container, false)
        btnNavigate = v.findViewById(R.id.btnNav1)
        return v
    }


    override fun onStart() {
        super.onStart()
        btnNavigate.setOnClickListener {
            findNavController().navigateUp()


        }
    }
}