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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDdetailFragment() : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: UserDetailViewModel by viewModels()
    lateinit var v: View
    lateinit var txtUserlastname: EditText
    lateinit var txtUserName: EditText
    lateinit var txtUserEmail: EditText
    lateinit var txtPassword: EditText
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
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


        return v
    }


    override fun onStart() {
        super.onStart()

        var auxUser:User = User("", "", "", "", "") // Instance un User
        val getUser: User = viewModel.getUser()

        txtUserName.setText(getUser.name)
        txtUserlastname.setText(getUser.lastname)
        txtUserEmail.setText(getUser.email)
        txtPassword.setText(getUser.password)


        updatebtn.setOnClickListener{
            val username = txtUserName.text.toString()
            val lastname = txtUserlastname.text.toString()
            val pass = txtPassword.text.toString()
            val email = txtUserEmail.text.toString()

            auxUser.name = username
            auxUser.lastname = lastname
            auxUser.password = pass
            auxUser.email = email

            viewModel.updateUser(auxUser)


        }

    }
}
//        updatebtn.setOnClickListener {
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
//}





