package com.utn.firstapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserSignup : Fragment() {

    private val viewModel: UserSignupViewModel by viewModels()

    lateinit var v: View

    lateinit var addUserEmail: TextInputLayout
    lateinit var addUserPassword: TextInputLayout
    lateinit var addUserConfPasswd: TextInputLayout
    lateinit var addUserbtn: Button
    lateinit var loadingPb : ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_signup2, container, false)

        addUserEmail = v.findViewById(R.id.edtSgnUpEmail)
        addUserPassword = v.findViewById(R.id.edtLoginSgnUpPassword)
        addUserConfPasswd = v.findViewById(R.id.edtSgnUpConfirmPassword)
        addUserbtn = v.findViewById(R.id.btnSgnUpAddUser)
        loadingPb = v.findViewById(R.id.signUpProgressBar)


        //Password stars hidden
        addUserPassword.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        addUserPassword.editText?.text = addUserPassword.editText?.text

        addUserConfPasswd.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        addUserConfPasswd.editText?.text = addUserConfPasswd.editText?.text

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
                    addUserEmail.editText?.setText("")
                    addUserPassword.editText?.setText("")
                    addUserConfPasswd.editText?.setText("")
                }

                State.PASSLENGTH ->
                {
                    Snackbar.make(v, "Password requires 6 characters at least", Snackbar.LENGTH_SHORT).show()
                    addUserPassword.editText?.setText("")
                    addUserConfPasswd.editText?.setText("")
                }

                State.PASSNOTEQUAL ->
                {
                    Snackbar.make(v, "Password must match each other", Snackbar.LENGTH_SHORT).show()
                    addUserPassword.editText?.setText("")
                    addUserConfPasswd.editText?.setText("")
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
                addUserEmail.editText?.text.toString(),
                addUserPassword.editText?.text.toString(),
                addUserConfPasswd.editText?.text.toString(),
            )


        }
    }
}