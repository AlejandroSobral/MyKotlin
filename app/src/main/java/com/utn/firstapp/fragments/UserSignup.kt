package com.utn.firstapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserSignup : Fragment() {

    private val viewModel: UserSignupViewModel by viewModels()

    lateinit var v: View
    lateinit var addUserName: EditText
    lateinit var addUserlastname: EditText
    lateinit var addUserEmail: EditText
    lateinit var addUserPassword: EditText
    lateinit var addUserbtn: Button

    //private var db: AppDatabase? = null
    private var userDao: UserDao? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_signup2, container, false)

        addUserName = v.findViewById(R.id.edtSgnUpName)
        addUserEmail = v.findViewById(R.id.edtSgnUpEmail)
        addUserPassword = v.findViewById(R.id.edtSgnUpPassword)
        addUserlastname = v.findViewById(R.id.edtSgnUplastname)
        addUserbtn = v.findViewById(R.id.btnSgnUpAddUser)

        return v
    }

    override fun onStart() {
        super.onStart()

        /* Old room DB
        lateinit var usertypeIn: User
        //db = AppDatabase.getInstance(v.context)
        //userDao = db?.userDao()

        // Dummy call to pre-populate db
        //userDao?.fetchAllUsers()*/


        addUserbtn.setOnClickListener {

            lateinit var userFind:User


            var auxUser = User(
                "",
                addUserName.text.toString(),
                addUserlastname.text.toString(),
                addUserEmail.text.toString(),
                addUserPassword.text.toString(),
                "0"

            )

            //val userFind = userDao?.fetchUserByUserAndMail(username, usermail)

            userFind = viewModel.getUserFromNameAndPass(auxUser.name, auxUser.password)
            if (userFind.name == "")
                try {
                    viewModel.insertUserAuthv2(auxUser)                     //ADD USER TO DB

                    val message = "User, ${auxUser.name}, has been added correctly!"

                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                    addUserName.setText("")
                    addUserEmail.setText("")
                    addUserPassword.setText("")
                    addUserlastname.setText("")

                    val intent = Intent(activity, SecondActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(v, "Error on Signing Up", Snackbar.LENGTH_SHORT).show()

                }

        }
    }


}