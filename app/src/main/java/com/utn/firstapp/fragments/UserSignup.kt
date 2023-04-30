package com.utn.firstapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao


class UserSignup : Fragment() {
    lateinit var v: View
    lateinit var addUserName : EditText
    lateinit var addUserLastName : EditText
    lateinit var addUserEmail : EditText
    lateinit var addUserPassword : EditText
    lateinit var addUserbtn : Button
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    private lateinit var viewModel: UserSignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_user_signup2, container, false)

        addUserName = v.findViewById(R.id.edtSgnUpName)
        addUserEmail = v.findViewById(R.id.edtSgnUpEmail)
        addUserPassword = v.findViewById(R.id.edtSgnUpPassword)
        addUserLastName = v.findViewById(R.id.edtSgnUpLastName)
        addUserbtn = v.findViewById(R.id.btnSgnUpAddUser)

        return v
    }

    override fun onStart() {
        super.onStart()

        lateinit var usertypeIn: User
        db = AppDatabase.getInstance(v.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()


        addUserbtn.setOnClickListener {

                var username: String = addUserName.text.toString()
                var usermail: String = addUserEmail.text.toString()
                var userpass: String = addUserPassword.text.toString()
                var userlastname: String = addUserLastName.text.toString()


                val userFind = userDao?.fetchUserByUserAndMail(username, usermail)
                if (userFind == null)
                    try {
                        userDao?.insertUser(User(0, username, userlastname, usermail, userpass))

                        val message = "User, ${username}, has been added correctly!"
                        //ADD USER TO DB
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                        addUserName.setText("")
                        addUserEmail.setText("")
                        addUserPassword.setText("")
                        addUserLastName.setText("")
                        val intent = Intent(activity, SecondActivity::class.java)
                        startActivity(intent)
                    }
                    catch (e: Exception)
                    {
                        Snackbar.make(v, "Error on Signing Up", Snackbar.LENGTH_SHORT).show()

                    }

                }
            }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserSignupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}