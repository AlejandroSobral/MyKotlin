package com.utn.firstapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.database.AppDatabase


class UserDdetailFragment : Fragment() {    private lateinit var viewModel: UserDetailViewModel

    lateinit var v: View
    lateinit var txtUserLastName: EditText
    lateinit var txtUserName: EditText
    lateinit var txtUserEmail: EditText
    lateinit var txtPassword: EditText
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    lateinit var updatebtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_user_ddetail, container, false)

        txtUserName = v.findViewById(R.id.txtUserNameDetail)
        txtUserLastName = v.findViewById(R.id.txtUserLastNameDetail)
        txtUserEmail = v.findViewById(R.id.txtUserEmailDetail)
        txtPassword =  v.findViewById(R.id.txtPasswordDetail)
        updatebtn = v.findViewById(R.id.btnUpdateUser)


        return v
    }

    override fun onStart(){
        super.onStart()
        lateinit var usertypeIn: User
        db = AppDatabase.getInstance(v.context)
        userDao = db?.userDao()

        userDao?.fetchAllUsers()


        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val idUser = sharedPref!!.getInt("UserID", 0)

        try {
            usertypeIn = userDao?.fetchUserById(idUser) as User

            txtUserName.setText(usertypeIn.name)
            txtUserLastName.setText(usertypeIn.lastName)
            txtUserEmail.setText(usertypeIn.email)
            txtPassword.setText(usertypeIn.password)


        }
        catch(e:Exception)
        {
            Snackbar.make(v, "Wrong try getting User info.", Snackbar.LENGTH_SHORT).show()}

        updatebtn.setOnClickListener {


            if (idUser != null) {
                    val username = txtUserName.text.toString()
                    val lastname = txtUserLastName.text.toString()
                    val pass = txtPassword.text.toString()
                    val email = txtUserEmail.text.toString()

                    usertypeIn.name = username
                    usertypeIn.lastName = lastname
                    usertypeIn.password = pass
                    usertypeIn.email = email

                userDao?.updateUser(usertypeIn)
                Snackbar.make(v, "User data correctly updated", Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}





