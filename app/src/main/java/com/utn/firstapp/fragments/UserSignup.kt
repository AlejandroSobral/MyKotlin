package com.utn.firstapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserSignup : Fragment() {

    private val viewModel: UserSignupViewModel by viewModels()

    lateinit var v: View
    lateinit var addUserName: EditText
    lateinit var addUserEmail: EditText
    lateinit var addUserPassword: EditText
    lateinit var addUserConfPasswd: EditText
    lateinit var addUserbtn: Button
    lateinit var loadingPb : ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_signup2, container, false)

        addUserName = v.findViewById(R.id.edtSgnUpName)
        addUserEmail = v.findViewById(R.id.edtSgnUpEmail)
        addUserPassword = v.findViewById(R.id.edtSgnUpPassword)
        addUserConfPasswd = v.findViewById(R.id.edtSgnUpConfirmPassword)
        addUserbtn = v.findViewById(R.id.btnSgnUpAddUser)
        loadingPb = v.findViewById(R.id.signUpProgressBar)



        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {

                State.LOADING -> {
                    loadingPb.visibility = View.VISIBLE;
                    addUserEmail.visibility = View.INVISIBLE
                    addUserPassword.visibility = View.INVISIBLE
                    addUserConfPasswd.visibility = View.INVISIBLE
                    addUserPassword.visibility = View.INVISIBLE
                }

                State.FAILURE -> {
                    Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
                    addUserEmail.visibility = View.VISIBLE
                    addUserPassword.visibility = View.VISIBLE
                    addUserConfPasswd.visibility = View.VISIBLE
                    addUserPassword.visibility = View.VISIBLE
                    loadingPb.visibility = View.GONE;
                }

                State.SUCCESS -> {
                    val intent = Intent(activity, SecondActivity::class.java)
                    startActivity(intent)
                    addUserEmail.setText("")
                    addUserPassword.setText("")
                    addUserConfPasswd.setText("")
                }

                State.PASSLENGTH ->
                {
                    Snackbar.make(v, "Password requires 6 characters at least", Snackbar.LENGTH_SHORT).show()
                    addUserPassword.setText("")
                    addUserConfPasswd.setText("")
                }

                State.PASSNOTEQUAL ->
                {
                    Snackbar.make(v, "Password must match each other", Snackbar.LENGTH_SHORT).show()
                    addUserPassword.setText("")
                    addUserConfPasswd.setText("")
                }

                else -> {}
            }
        }

        return v
    }

    override fun onStart() {
        super.onStart()


        addUserbtn.setOnClickListener {

            viewModel.userSignUpCor(
                addUserEmail.text.toString(),
                addUserPassword.text.toString(),
                addUserConfPasswd.text.toString()
            )

            /* OLD METHOD NO-COR

            userFind = viewModel.getUserFromNameAndPass(addUserName.text.toString(), addUserPassword.text.toString())
            if (userFind.name == "")
                try {
                    viewModel.insertUserAuthv2(auxUser)                     //ADD USER TO DB

                    val message = "User, ${auxUser.name}, has been added correctly!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                    addUserName.setText("")
                    addUserEmail.setText("")
                    addUserPassword.setText("")
                    //addUserlastname.setText("")

                    val intent = Intent(activity, SecondActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(v, "Error on Signing Up", Snackbar.LENGTH_SHORT).show()
                }*/
        }
    }
}