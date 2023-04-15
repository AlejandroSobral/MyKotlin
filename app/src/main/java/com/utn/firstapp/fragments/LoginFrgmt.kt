package com.utn.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.entities.User

class LoginFrgmt : Fragment() {

    companion object {
        fun newInstance() = LoginFrgmt()
    }
    lateinit var imgLoginLogo : ImageView
    lateinit var label : TextView
    lateinit var btnNavigate: Button
    lateinit var v: View
    lateinit var inputuser: EditText
    lateinit var inputpass: EditText
    var imgLoginLogoURL: String = "https://upload.wikimedia.org/wikipedia/commons/8/85/Logo_lpf_afa.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_screen1, container, false)

        label = v.findViewById(R.id.txtScreen1)
        btnNavigate = v.findViewById(R.id.btnNav2)
        inputuser = v.findViewById(R.id.txtInput_user)
        inputpass = v.findViewById(R.id.txtInput_password)
        imgLoginLogo = v.findViewById(R.id.imgLoginLogo)
        Glide.with(v).load(imgLoginLogoURL).into(imgLoginLogo)
        return v

    }

    override fun onStart(){
        super.onStart()
        btnNavigate.setOnClickListener{


            val action = LoginFrgmtDirections.actionLoginFragmentToHomeFragment()


            var rightUser: User = User("First", "User", "asd", "1") // Instance un User
            var inputTextuser: String = inputuser.text.toString()
            var inputTextpass: String = inputpass.text.toString()

            if(inputTextuser == rightUser.email && inputTextpass == rightUser.password  ) {
                findNavController().navigate(action)
            }
            else
                Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
        }

    }


}