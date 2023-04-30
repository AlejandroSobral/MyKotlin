package com.utn.firstapp.fragments



import android.content.Context
import com.utn.firstapp.database.AppDatabase
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User
import java.lang.Error


class LoginFrgmt : Fragment() {

    companion object {
        fun newInstance() = LoginFrgmt()
    }

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var clubdao: ClubDao? = null
    lateinit var imgLoginLogo : ImageView
    lateinit var label : TextView
    lateinit var btnNavigate: Button
    lateinit var btnSignUp : Button
    lateinit var v: View
    lateinit var inputuser: EditText
    lateinit var inputpass: EditText
    var imgLoginLogoURL: String = "https://upload.wikimedia.org/wikipedia/commons/8/85/Logo_lpf_afa.png"
    private lateinit var userList: MutableList<User>


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
        Glide.with(v).load(imgLoginLogoURL).into(imgLoginLogo)
        return v

    }

    override fun onStart(){
        super.onStart()

        db = AppDatabase.getInstance(v.context)
        userDao = db?.userDao()
        clubdao = db?.clubDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        val clubList = clubdao?.fetchAllClubs()


        btnSignUp.setOnClickListener{



            val action = LoginFrgmtDirections.actionLoginFragmentToUserSignup2()
            findNavController().navigate(action)
        }

        btnNavigate.setOnClickListener {

            lateinit var usertypeIn: User
            //var rightUser: User = User(0, "User", "asd", "1", "asd") // Instance un User
            var inputTextuser: String = inputuser.text.toString()
            var inputTextpass: String = inputpass.text.toString()

            try {
                usertypeIn = userDao?.fetchUserByUserAndPass(inputTextuser, inputTextpass) as User

                if(usertypeIn.name == usertypeIn.name && usertypeIn.password == usertypeIn.password)
                {
                    val sharedPref = context?.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE)

                    if (sharedPref != null) {
                        with (sharedPref.edit()) {
                            putInt("UserID", usertypeIn.id)
                            commit()
                        }
                    }



                    val intent = Intent(activity, SecondActivity::class.java)
                    intent.putExtra("CurrentUserID", usertypeIn.id)
                    startActivity(intent)
                }

            }
            catch(e:Exception)
            {Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()}

            //userList = userDao?.fetchAllUsers() as MutableList<User>



        }


            //if(inputTextuser == rightUser.email && inputTextpass == rightUser.password  ) {
              //  val intent = Intent(activity, SecondActivity::class.java)
                //intent.putExtra("fragmentId", R.id.HomeFragment)
                //intent.putExtra("bundle_key", userBundle)

                //startActivity(intent)
            //}
            //else
                //Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
        //}

    }


}