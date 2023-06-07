package com.utn.firstapp.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.play.core.integrity.v
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.entities.State

class RecuPassFrgmt : Fragment() {

    lateinit var imgHomeLogo: ImageView
    lateinit var v: View
    private val viewModel: RecuPassFrgmtViewModel by viewModels()
    lateinit var txtEmail : TextInputLayout
    lateinit var sendButton: Button
    lateinit var loadingPb : ProgressBar
    var imgHomeLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_recu_pass_frgmt, container, false)

        imgHomeLogo = v.findViewById(R.id.RecPassimgLoginLogo)
        txtEmail = v.findViewById(R.id.RecPassedtLoginSgnUpPassword)
        sendButton = v.findViewById(R.id.RecPassbtnRecPass)
        loadingPb = v.findViewById(R.id.RecProgressBar)
        Glide.with(v).load(imgHomeLogoURL).into(imgHomeLogo)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {

                    loadingPb.visibility = View.INVISIBLE
                    txtEmail.visibility = View.VISIBLE
                    txtEmail.editText?.setText("")
                    Snackbar.make(v, "Email has been sent.", Snackbar.LENGTH_SHORT).show()
                }

                State.LOADING -> {
                    loadingPb.visibility = View.VISIBLE
                    txtEmail.visibility = View.INVISIBLE

                }

                State.FAILURE -> {
                    loadingPb.visibility = View.INVISIBLE
                    txtEmail.visibility = View.VISIBLE
                    txtEmail.editText?.setText("")
                    Snackbar.make(v, "Send email has failed.", Snackbar.LENGTH_SHORT).show()

                }

                else -> {}
            }
        }


        return v

    }

    override fun onStart() {
        super.onStart()

        sendButton.setOnClickListener {
            viewModel.mySendEmail(txtEmail.editText?.text.toString())

        }

    }


}