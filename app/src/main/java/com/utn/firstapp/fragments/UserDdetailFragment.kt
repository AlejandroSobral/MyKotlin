package com.utn.firstapp.fragments

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.utn.firstapp.R
import com.utn.firstapp.activities.MainActivity
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDdetailFragment() : Fragment() {


    private val viewModel: UserDetailViewModel by viewModels()
    lateinit var v: View
    lateinit var mainText: TextView
    lateinit var txtUserOldPass: TextInputLayout
    lateinit var txtPassword: TextInputLayout
    lateinit var txtPasswordCheck: TextInputLayout
    lateinit var loadingPb: ProgressBar
    lateinit var updatebtn: Button
    lateinit var updateProfPicebtn: Button
    lateinit var viewProfPicbtn: Button
    lateinit var profilePic: ImageView
    lateinit var viewProfPic: ImageView
    lateinit var btnLogOut: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_ddetail, container, false)



        txtUserOldPass = v.findViewById(R.id.edtUserDetailOldPass)
        txtPassword = v.findViewById(R.id.edtUserDetailTextPass)
        txtPasswordCheck = v.findViewById(R.id.edtUserDetailTextPassCheck)
        updatebtn = v.findViewById(R.id.btnUpdateUser)
        loadingPb = v.findViewById(R.id.loadingUserDetailprogressBar)
        profilePic = v.findViewById(R.id.profilePic)
        updateProfPicebtn = v.findViewById(R.id.btnUpdateProPic)
        viewProfPicbtn = v.findViewById(R.id.btnViewProPic)
        viewProfPic = v.findViewById(R.id.ViewProfilePicImg)
        mainText = v.findViewById(R.id.txtUserDetailText)

        btnLogOut = v.findViewById(R.id.btnLogOut)

        txtUserOldPass.visibility = View.VISIBLE
        txtPassword.visibility = View.VISIBLE
        txtPasswordCheck.visibility = View.VISIBLE

        viewModel.ViewProfilePicture.observe(viewLifecycleOwner) { ViewPicture ->

            if (ViewPicture != null) {

                viewProfPic.visibility = View.VISIBLE
                viewProfPicbtn.visibility = View.INVISIBLE
                updateProfPicebtn.visibility = View.INVISIBLE
                txtPassword.visibility = View.INVISIBLE
                txtUserOldPass.visibility = View.INVISIBLE
                txtPasswordCheck.visibility = View.INVISIBLE
                updatebtn.visibility = View.INVISIBLE
                mainText.visibility = View.VISIBLE
                profilePic.visibility = View.INVISIBLE
                btnLogOut.visibility = View.INVISIBLE


                Glide.with(v).load(ViewPicture).into(viewProfPic)


                val handler = Handler()
                handler.postDelayed({
                    viewProfPic.visibility = View.INVISIBLE
                    viewProfPicbtn.visibility = View.VISIBLE
                    updateProfPicebtn.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    updatebtn.visibility = View.VISIBLE
                    mainText.visibility = View.VISIBLE
                    profilePic.visibility = View.VISIBLE
                    btnLogOut.visibility = View.VISIBLE

                }, 1500)


            }
            if (ViewPicture == null) {
                viewProfPic.visibility = View.GONE
                //val drawable = resources.getDrawable(R.drawable.user)
                //Glide.with(v).load(drawable).into(viewProfPic)
            }


        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    Snackbar.make(v, "User has been updated properly.", Snackbar.LENGTH_SHORT)
                        .show()
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

                State.PASSLENGTH -> {
                    Snackbar.make(
                        v,
                        "Password requires 6 characters at least",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    txtPassword.editText?.setText("")
                    txtPasswordCheck.editText?.setText("")
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE
                }

                State.PASSNOTEQUAL -> {
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

        viewModel.profilePic.observe(viewLifecycleOwner) { getprofilePic ->

            if (getprofilePic != null) {
                profilePic.visibility = View.VISIBLE
                Glide.with(v).load(getprofilePic).into(profilePic)
            }
            if (getprofilePic == null) {
                profilePic.visibility = View.VISIBLE
                val drawable = resources.getDrawable(R.drawable.user)

                Glide.with(v).load(drawable).into(profilePic)

            }
        }

        viewModel.profilePicstate.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    loadingPb.visibility = View.INVISIBLE
                    profilePic.visibility = View.VISIBLE

                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE
                    //Snackbar.make(v, "Profile picture has been updated", Snackbar.LENGTH_SHORT).show()


                }

                State.LOADING -> {
                    loadingPb.visibility = View.VISIBLE
                    profilePic.visibility = View.INVISIBLE

                    txtUserOldPass.visibility = View.INVISIBLE
                    txtPassword.visibility = View.INVISIBLE
                    txtPasswordCheck.visibility = View.INVISIBLE


                }

                State.FAILURE -> {
                    loadingPb.visibility = View.INVISIBLE
                    profilePic.visibility = View.VISIBLE
                    txtUserOldPass.visibility = View.VISIBLE
                    txtPassword.visibility = View.VISIBLE
                    txtPasswordCheck.visibility = View.VISIBLE

                    Snackbar.make(v, "Profile picture could not be reached", Snackbar.LENGTH_SHORT)
                        .show()

                }

                else -> {}
            }
        }


        return v
    }

    override fun onStart() {
        super.onStart()

        //Password stars hidden
        txtPassword.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        txtPassword.editText?.text = txtPassword.editText?.text

        txtPasswordCheck.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        txtPasswordCheck.editText?.text = txtPasswordCheck.editText?.text

        val getUser = viewModel.getUser()
        viewModel.myGetUserProfilePicCor()




        updatebtn.setOnClickListener {

            viewModel.myUpdatePassFirebaseUser(
                getUser!!.email,
                txtUserOldPass.editText?.text.toString(),
                txtPassword.editText?.text.toString(),
                txtPasswordCheck.editText?.text.toString()
            )

        }

        updateProfPicebtn.setOnClickListener {
            takePicture(v)
        }

        viewProfPicbtn.setOnClickListener {

            viewModel.myViewProfilePicCor()

        }


        btnLogOut.setOnClickListener {
            val context = requireContext()
            val builder = AlertDialog.Builder(context)

            var currentUser = viewModel.getUser()
            if (currentUser != null) { // When logging out, position returns to zero.
                currentUser.lastposition = "0"
                viewModel.updateUserPref(currentUser)
            }

            // set the message and title of the dialog
            builder.setMessage("Are you sure you want to log out?")
                .setTitle("Logout")
            // add buttons to the dialog
            builder.setPositiveButton("Accept",
                DialogInterface.OnClickListener { dialog, id ->
                    // user clicked Accept button
                    // do your logout logic here
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("fragmentId", R.id.LoginFragment)
                    startActivity(intent)

                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    // user cancelled the dialog
                    dialog.dismiss()
                })

            // create and show the dialog
            val dialog = builder.create()
            dialog.show()
        }
    }

    fun takePicture(view: View) {

        val camReq = 1
        // Create an intent to start the camera.
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Start the camera activity.
        startActivityForResult(intent, camReq)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val camReq = 1
        if (requestCode == camReq && resultCode == RESULT_OK) {

            // Get the image from the camera.
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            if (imageBitmap != null) {
                viewModel.uploadStorageImage(imageBitmap)
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





