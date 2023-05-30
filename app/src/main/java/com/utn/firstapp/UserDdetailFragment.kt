package com.utn.firstapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.State
import com.utn.firstapp.entities.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDdetailFragment() : Fragment() {


    private val viewModel: UserDetailViewModel by viewModels()
    lateinit var v: View
    lateinit var txtUserlastname: EditText
    lateinit var txtUserName: EditText
    lateinit var txtUserEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var loadingPb: ProgressBar
    lateinit var updatebtn: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_ddetail, container, false)

        txtUserName = v.findViewById(R.id.txtUserNameDetail)
        txtUserlastname = v.findViewById(R.id.txtUserlastnameDetail)
        txtUserEmail = v.findViewById(R.id.txtUserEmailDetail)
        txtPassword = v.findViewById(R.id.txtPasswordDetail)
        updatebtn = v.findViewById(R.id.btnUpdateUser)
        loadingPb = v.findViewById(R.id.loadingUserDetailprogressBar)


        return v
    }


    override fun onStart() {
        super.onStart()
        loadingPb.visibility = View.VISIBLE
        txtUserName.visibility = View.INVISIBLE
        txtUserlastname.visibility = View.INVISIBLE
        txtUserEmail.visibility = View.INVISIBLE
        txtPassword.visibility = View.INVISIBLE

        val getUser: User? = viewModel.getUser()

        txtUserName.setText(getUser?.name)
        txtUserlastname.setText(getUser?.lastname)
        txtUserEmail.setText(getUser?.email)
        txtPassword.setText(getUser?.password)

        txtUserName.visibility = View.VISIBLE
        txtUserlastname.visibility = View.VISIBLE
        txtUserEmail.visibility = View.VISIBLE
        txtPassword.visibility = View.VISIBLE
        loadingPb.visibility = View.GONE


        updatebtn.setOnClickListener {


            if (getUser != null) {
                getUser.name = txtUserName.text.toString()
                getUser.lastname = txtUserlastname.text.toString()
                getUser.password = txtPassword.text.toString()
                getUser.email = txtUserEmail.text.toString()

                viewModel.updateUser(getUser)

                viewModel.state.observe(this) { state ->
                    when (state) {
                        State.SUCCESS -> {
                            Snackbar.make(v, "User has been edited properly.", Snackbar.LENGTH_SHORT).show()
                            txtUserName.visibility = View.VISIBLE
                            txtUserlastname.visibility = View.VISIBLE
                            txtUserEmail.visibility = View.VISIBLE
                            txtPassword.visibility = View.VISIBLE
                            loadingPb.visibility = View.INVISIBLE
                        }

                        State.FAILURE -> {
                            Snackbar.make(v, "User edit has failed", Snackbar.LENGTH_SHORT).show()
                        }

                        State.LOADING -> {
                            //Snackbar.make(v, "Loading", Snackbar.LENGTH_SHORT).show()
                            loadingPb.visibility = View.VISIBLE
                            txtUserName.visibility = View.INVISIBLE
                            txtUserlastname.visibility = View.INVISIBLE
                            txtUserEmail.visibility = View.INVISIBLE
                            txtPassword.visibility = View.INVISIBLE
                        }

                        null -> {
                        }
                    }
                }
            }


        }

    }
}
/*        updatebtn.setOnClickListener {
//
//
//            val context = requireContext()
//            val builder = AlertDialog.Builder(context)
//
//            // set the message and title of the dialog
//            builder.setMessage("Are you sure you want to update user information?")
//                .setTitle("Logout")
//
//            // add buttons to the dialog
//            builder.setPositiveButton("Accept",
//                DialogInterface.OnClickListener { dialog, id ->
//                    // user clicked Accept button
//                    // do your logout logic here
//                    if (idUser != null) {
//                        val username = txtUserName.text.toString()
//                        val lastname = txtUserlastname.text.toString()
//                        val pass = txtPassword.text.toString()
//                        val email = txtUserEmail.text.toString()
//
//                        usertypeIn.name = username
//                        usertypeIn.lastname = lastname
//                        usertypeIn.password = pass
//                        usertypeIn.email = email
//
//                        userDao?.updateUser(usertypeIn)
//                        Snackbar.make(v, "User data correctly updated", Snackbar.LENGTH_SHORT).show()
//                    }
//
//                })
//            builder.setNegativeButton("Cancel",
//                DialogInterface.OnClickListener { dialog, id ->
//                    // user cancelled the dialog
//                    dialog.dismiss()
//                })
//
//            // create and show the dialog
//            val dialog = builder.create()
//            dialog.show()
//
//        }
//
//    }
//}*/





