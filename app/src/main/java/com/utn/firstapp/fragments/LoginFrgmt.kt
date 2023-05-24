package com.utn.firstapp.fragments



import android.content.ContentValues
import android.content.Context
import com.utn.firstapp.database.AppDatabase
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.utn.firstapp.R
import com.utn.firstapp.activities.SecondActivity
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error
import javax.inject.Inject


@AndroidEntryPoint
class LoginFrgmt : Fragment() {

    companion object {
        fun newInstance() = LoginFrgmt()
    }
    private val viewModel: LoginFrgmtViewModel by viewModels()

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var clubdao: ClubDao? = null
    lateinit var imgLoginLogo: ImageView
    lateinit var label: TextView
    lateinit var btnNavigate: Button
    lateinit var btnSignUp: Button
    lateinit var v: View
    lateinit var inputuser: EditText
    lateinit var inputpass: EditText
    var imgLoginLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"
    private lateinit var userList: MutableList<User>
    private lateinit var userIntList: MutableList<User>


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

    override fun onStart() {
        super.onStart()

        //val userIntList = mutableListOf<User>()

//        db_int.collection("users")
//            //.whereEqualTo("userName", inputTxtUserName)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("TestDB", "${document.id} => ${document.data}")
//
//                    auxUser.id = document.getString("id") ?: ""
//                    auxUser.name = document.getString("name") ?: ""
//                    auxUser.email = document.getString("email") ?: ""
//                    auxUser.lastname = document.getString("lastname") ?: ""
//                    auxUser.password = document.getString("password") ?: ""
//
//
//                    userIntList.add(auxUser)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TestDB", "Error getting documents: ", exception)
//            }

        //val TAGINT = "DBINT"
        //db_int.collection("users")
        //  .get()
        //   .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
        //       if (task.isSuccessful) {
        //  for (document in task.result) {
        //       Log.d("TestDB", document.id + " => " + document.data)
        //        //Log.d("TestDB", document.key + " => " + document.data)
        //     }
        //  } else {
        //       Log.d("TestDB", "Error getting documents: ", task.exception)
        //    }
        // })


        db = AppDatabase.getInstance(v.context)
        userDao = db?.userDao()
        clubdao = db?.clubDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        val clubList = clubdao?.fetchAllClubs()


        btnSignUp.setOnClickListener {


            val action = LoginFrgmtDirections.actionLoginFragmentToUserSignup2()
            findNavController().navigate(action)
        }

        btnNavigate.setOnClickListener {

            //lateinit var usertypeIn: User
            //var rightUser: User = User(0, "User", "asd", "1", "asd") // Instance un User
            var inputTextuser: String = inputuser.text.toString()
            var inputTextpass: String = inputpass.text.toString()
            var auxUser:User = User("", "User", "asd", "1", "asd") // Instance un User

            viewModel.getUserFromUsernameAndPassword(inputTextuser, inputTextpass)


            viewModel.user.observe(viewLifecycleOwner) { currentUser ->

                if (currentUser != null) {

                    Log.d("TestDB", "ID:"+currentUser.id)

                    inputuser.setText("")
                    inputpass.setText("")
                    val intent = Intent(activity, SecondActivity::class.java)
                    intent.putExtra("CurrentUserID", currentUser.id)
                    startActivity(intent)
                } else {
                    Snackbar.make(v, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
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
    }
}