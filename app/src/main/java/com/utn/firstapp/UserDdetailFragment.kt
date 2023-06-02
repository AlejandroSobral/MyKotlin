package com.utn.firstapp

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
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDdetailFragment() : Fragment() {


    private val viewModel: UserDetailViewModel by viewModels()
    lateinit var v: View
    lateinit var txtUserOldPass: TextInputLayout
    lateinit var txtPassword: TextInputLayout
    lateinit var txtPasswordCheck: TextInputLayout
    lateinit var loadingPb: ProgressBar
    lateinit var updatebtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_ddetail, container, false)


        txtUserOldPass = v.findViewById(R.id.edtUserDetailOldPass)
        txtPassword = v.findViewById(R.id.edtUserDetailTextPass)
        txtPasswordCheck = v.findViewById(R.id.edtUserDetailTextPassCheck)
        updatebtn = v.findViewById(R.id.btnUpdateUser)
        loadingPb = v.findViewById(R.id.loadingUserDetailprogressBar)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    Snackbar.make(v, "User has been updated properly.", Snackbar.LENGTH_SHORT).show()
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE
                    txtPassword.editText?.setText("")
                    txtPasswordCheck.editText?.setText("")
                    txtUserOldPass.editText?.setText("")
                }
                State.FAILURE -> {
                    Snackbar.make(v, "User update has failed", Snackbar.LENGTH_SHORT).show()
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE
                    txtPassword.editText?.setText("")
                    txtPasswordCheck.editText?.setText("")
                    txtUserOldPass.editText?.setText("")
                }
                State.LOADING -> {
                    //Snackbar.make(v, "Loading", Snackbar.LENGTH_SHORT).show()
                    loadingPb.visibility = View.VISIBLE
                    txtPassword.visibility = View.INVISIBLE
                    txtPasswordCheck.visibility = View.INVISIBLE
                    txtUserOldPass.visibility = View.INVISIBLE

                }

                State.PASSLENGTH ->
                {
                    Snackbar.make(v, "Password requires 6 characters at least", Snackbar.LENGTH_SHORT).show()
                    txtPassword.editText?.setText("")
                    txtPasswordCheck.editText?.setText("")
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE
                }

                State.PASSNOTEQUAL ->
                {
                    Snackbar.make(v, "Password does not match", Snackbar.LENGTH_SHORT).show()
                    txtPassword.editText?.setText("")
                    txtPasswordCheck.editText?.setText("")
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE
                }
                null -> {
                }
                else -> {}
            }
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        loadingPb.visibility = View.VISIBLE
        txtUserOldPass.visibility = View.INVISIBLE
        txtPassword.visibility = View.INVISIBLE
        txtPasswordCheck.visibility = View.INVISIBLE

        //Password stars hidden
        txtPassword.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        txtPassword.editText?.text = txtPassword.editText?.text

        txtPasswordCheck.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        txtPasswordCheck.editText?.text = txtPasswordCheck.editText?.text

        val getUser = viewModel.getUser()


        txtUserOldPass.visibility = View.VISIBLE
        txtPassword.visibility = View.VISIBLE
        txtPasswordCheck.visibility = View.VISIBLE
        loadingPb.visibility = View.GONE


        updatebtn.setOnClickListener {

                viewModel.myUpdatePassFirebaseUser(getUser!!.email,
                    txtUserOldPass.editText?.text.toString(),
                    txtPassword.editText?.text.toString(),
                    txtPasswordCheck.editText?.text.toString()
                )

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





