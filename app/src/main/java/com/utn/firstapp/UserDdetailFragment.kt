package com.utn.firstapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.utn.firstapp.entities.User
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.database.AppDatabase


class UserDdetailFragment : Fragment() {    private lateinit var viewModel: UserDetailViewModel

    lateinit var v: View
    lateinit var txtUserLastName: TextView
    lateinit var txtUserName: TextView
    lateinit var txtUserFounded: TextView
    lateinit var txtUserEmail: TextView
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_user_ddetail, container, false)

        txtUserName = v.findViewById(R.id.txtUserNameDetail)
        txtUserLastName = v.findViewById(R.id.txtUserLastNameDetail)
        txtUserEmail = v.findViewById(R.id.txtUserEmailDetail)


        return v
    }

    override fun onStart(){
        super.onStart()
        lateinit var usertypeIn: User
        db = AppDatabase.getInstance(v.context)
        var currentUserName = requireActivity().intent.getBundleExtra("CurrentUserName")

        var inputTextuser: String = currentUserName.toString()

        usertypeIn = userDao?.fetchUserByUserName(inputTextuser) as User

        if(usertypeIn!=null) {
            txtUserName.text = usertypeIn.name
            txtUserLastName.text = usertypeIn.lastName
            txtUserEmail.text = usertypeIn.email

        }

        //Glide.with(v).load(getclub.imageURL).into(imgClubDetail)


    }

}