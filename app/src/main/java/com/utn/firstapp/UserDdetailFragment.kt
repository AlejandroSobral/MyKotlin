package com.utn.firstapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.database.AppDatabase
import javax.inject.Inject


class UserDdetailFragment(context: Context) : Fragment() {

    @Inject
    lateinit var viewModel: UserDetailViewModel
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
        v = inflater.inflate(R.layout.fragment_user_ddetail, container, false)

        txtUserName = v.findViewById(R.id.txtUserNameDetail)
        txtUserLastName = v.findViewById(R.id.txtUserLastNameDetail)
        txtUserEmail = v.findViewById(R.id.txtUserEmailDetail)
        txtPassword = v.findViewById(R.id.txtPasswordDetail)
        updatebtn = v.findViewById(R.id.btnUpdateUser)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[UserDetailViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()

        lateinit var usertypeIn: User

        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner) { currentUser ->


            txtUserName.setText(currentUser.name)
            txtUserLastName.setText(currentUser.lastName)
            txtUserEmail.setText(currentUser.email)
            txtPassword.setText(currentUser.password)
        }


    }
}

//
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
//                        val lastname = txtUserLastName.text.toString()
//                        val pass = txtPassword.text.toString()
//                        val email = txtUserEmail.text.toString()
//
//                        usertypeIn.name = username
//                        usertypeIn.lastName = lastname
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





