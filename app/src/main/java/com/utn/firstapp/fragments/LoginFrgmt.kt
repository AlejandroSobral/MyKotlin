package com.utn.firstapp.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFrgmt : Fragment() {

    companion object {
        fun newInstance() = LoginFrgmt()
    }

    private val viewModel: LoginFrgmtViewModel by viewModels()
    lateinit var imgLoginLogo: ImageView
    lateinit var loadingPb: ProgressBar
    lateinit var label: TextView
    lateinit var btnNavigate: Button
    lateinit var btnSignUp: Button
    lateinit var v: View
    lateinit var inputuser: EditText
    lateinit var inputpass: EditText
    var imgLoginLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_screen1, container, false)

        label = v.findViewById(R.id.txtScreen1)
        btnNavigate = v.findViewById(R.id.btnNav2)
        btnSignUp = v.findViewById(R.id.btnSignUp)
        inputuser = v.findViewById(R.id.txtInput_user)
        inputpass = v.findViewById(R.id.txtInput_password)
        imgLoginLogo = v.findViewById(R.id.imgLoginLogo)
        loadingPb = v.findViewById(R.id.LoginProgressBar);
        Glide.with(v).load(imgLoginLogoURL).into(imgLoginLogo)

        return v

    }

    override fun onStart() {
        super.onStart()

        btnSignUp.setOnClickListener {

            val action = LoginFrgmtDirections.actionLoginFragmentToUserSignup2()
            findNavController().navigate(action)
        }

        btnNavigate.setOnClickListener {
            if (inputuser.text.toString() != "" && inputpass.text.toString() != "") {

                loadingPb.visibility = View.VISIBLE;
                btnSignUp.visibility = View.INVISIBLE
                btnNavigate.visibility = View.INVISIBLE
                inputpass.visibility = View.INVISIBLE
                inputuser.visibility = View.INVISIBLE

                viewModel.getAuthFromFirestone(inputuser.text.toString(), inputpass.text.toString())

                viewModel.user.observe(viewLifecycleOwner) { currentUser ->

                    if (currentUser != null) {
                        Log.d("TestDB", "ID:" + currentUser.uid)

                        val intent = Intent(activity, SecondActivity::class.java)
                        intent.putExtra("CurrentUserID", currentUser.uid)
                        startActivity(intent)
                        inputuser.setText("")
                        inputpass.setText("")
                    } else {
                        Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
                        btnSignUp.visibility = View.VISIBLE
                        btnNavigate.visibility = View.VISIBLE
                        inputpass.visibility = View.VISIBLE
                        inputuser.visibility = View.VISIBLE
                        loadingPb.visibility = View.GONE;
                    }
                }
            }
        }
    }
}