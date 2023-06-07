package com.utn.firstapp.fragments


import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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
import com.google.android.material.textfield.TextInputLayout
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text


@AndroidEntryPoint
class LoginFrgmt : Fragment() {

    companion object {
        fun newInstance() = LoginFrgmt()
    }
    private val viewModel: LoginFrgmtViewModel by viewModels()
    lateinit var txtRecuPass : TextView
    lateinit var imgLoginLogo: ImageView
    lateinit var loadingPb: ProgressBar
    lateinit var btnNavigate: Button
    lateinit var btnSignUp: Button
    lateinit var btnRecPass: Button
    lateinit var v: View
    lateinit var inputuser: TextInputLayout
    lateinit var inputpass: TextInputLayout
    var imgLoginLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_screen1, container, false)


        btnNavigate = v.findViewById(R.id.btnLogin)
        btnSignUp = v.findViewById(R.id.btnSignUp)
        inputuser = v.findViewById(R.id.edtLoginInputUser)
        inputpass = v.findViewById(R.id.edtLoginSgnUpPassword)
        imgLoginLogo = v.findViewById(R.id.imgLoginLogo)
        loadingPb = v.findViewById(R.id.LoginProgressBar);
        txtRecuPass = v.findViewById(R.id.txtRecPass)
        btnRecPass = v.findViewById(R.id.btnRecPass)
        Glide.with(v).load(imgLoginLogoURL).into(imgLoginLogo)

        //Password stars hidden
        inputpass.editText?.transformationMethod =
            PasswordTransformationMethod.getInstance()
        inputpass.editText?.text = inputpass.editText?.text

        /*viewModel.user.observe(viewLifecycleOwner) { currentUser ->

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
        }*/

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    val intent = Intent(activity, SecondActivity::class.java)
                    //intent.putExtra("CurrentUserID", currentUser.uid)
                    startActivity(intent)
                    inputuser.editText?.setText("")
                    inputpass.editText?.setText("")
                }

                State.LOADING -> {
                    loadingPb.visibility = View.VISIBLE;
                    btnSignUp.visibility = View.INVISIBLE
                    btnNavigate.visibility = View.INVISIBLE
                    inputpass.visibility = View.INVISIBLE
                    inputuser.visibility = View.INVISIBLE
                    txtRecuPass.visibility = View.INVISIBLE
                    btnRecPass.visibility = View.INVISIBLE
                }

                State.FAILURE -> {
                    Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
                    btnSignUp.visibility = View.VISIBLE
                    btnNavigate.visibility = View.VISIBLE
                    inputpass.visibility = View.VISIBLE
                    inputuser.visibility = View.VISIBLE
                    txtRecuPass.visibility = View.VISIBLE
                    btnRecPass.visibility = View.VISIBLE
                    loadingPb.visibility = View.GONE;
                }

                else -> {}
            }
        }
        return v
    }

    override fun onStart() {
        super.onStart()

        btnSignUp.setOnClickListener {
            val action = LoginFrgmtDirections.actionLoginFragmentToUserSignup2()

            findNavController().navigate(action)
        }


        btnRecPass.setOnClickListener {
            val action = LoginFrgmtDirections.actionLoginFragmentToRecuPassFrgmt()
            findNavController().navigate(action)
        }


        btnNavigate.setOnClickListener {
                /* OLD METHODS
                viewModel.getAuthFromFirestone(inputuser.text.toString(), inputpass.text.toString())

                viewModel.getAuthFromFirestoneCour(inputuser.text.toString(), inputpass.text.toString()) */
            inputuser.editText?.setText("asobralg1@gmail.com")
            inputpass.editText?.setText("123456")
                viewModel.myFirebaseLogin(inputuser.editText?.text.toString(), inputpass.editText?.text.toString())
        }
    }
}












