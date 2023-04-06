package com.utn.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.entities.User

class Screen1Fragment : Fragment() {

    companion object {
        fun newInstance() = Screen1Fragment()
    }

    lateinit var label : TextView
    lateinit var btnNavigate: Button
    lateinit var v: View
    lateinit var input_user: EditText
    lateinit var input_pass: EditText




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_screen1, container, false)

        label = v.findViewById(R.id.txtScreen1)
        btnNavigate = v.findViewById(R.id.btnNav2)
        input_user = v.findViewById(R.id.txtInput_user)
        input_pass = v.findViewById(R.id.txtInput_password)
        return v

    }

    override fun onStart(){
        super.onStart()
        btnNavigate.setOnClickListener{
            val action = Screen1FragmentDirections.actionScreen1FragmentToScreen2Fragment()


            var inputText_user: String = input_user.text.toString()
            var inputText_pass: String = input_pass.text.toString()
            var rightUser: User = User("First", "User", "asd@gmail.com", "MyPass") // Instance un User


            if(inputText_user == rightUser.email && inputText_pass == rightUser.password  ) {
                findNavController().navigate(action)
            }
            else
                Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
        }

    }


}