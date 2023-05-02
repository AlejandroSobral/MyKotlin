package com.utn.firstapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.firstapp.R
import com.utn.firstapp.activities.MainActivity
import com.utn.firstapp.adapters.ClubAdapter
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.ClubRepository




class HomeFragment : Fragment() {


    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var clubdao: ClubDao? = null
    lateinit var label: TextView
    //lateinit var btnNavigate: Button
    lateinit var v: View
    lateinit var recClubs: RecyclerView
    lateinit var adapter: ClubAdapter
    var imgHomeLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"
    lateinit var imgHomeLogo: ImageView
    lateinit var btnLogOut : Button
    lateinit var btnAddClub : Button


    var clubRepository: ClubRepository = ClubRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen2, container, false)
        //btnNavigate = v.findViewById(R.id.btnNav1)
        recClubs = v.findViewById(R.id.RecyView_Clubs)
        imgHomeLogo = v.findViewById(R.id.imgHomeLogo)
        btnLogOut = v.findViewById(R.id.btnLogOut)
        btnAddClub = v.findViewById(R.id.btnAddClub)
        Glide.with(v).load(imgHomeLogoURL).into(imgHomeLogo)



        return v
    }



    override fun onStart() {
        super.onStart()



        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        var prevPosition = sharedPref?.getInt("RecViewPos", 0)

        if(prevPosition==null){
            prevPosition = 0
        }


        val idUser = sharedPref?.getInt("UserID", 0)


        btnLogOut.setOnClickListener{


            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("fragmentId", R.id.LoginFragment)
           startActivity(intent)
        }

        btnAddClub.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddClub(clubRepository)
            findNavController().navigate(action)
        }


        db = AppDatabase.getInstance(v.context)
        userDao = db?.userDao()
        clubdao = db?.clubDao()
        //val clubList = clubdao?.fetchAllClubs()



        val clubList = clubdao?.fetchAllClubsOrderByName()


        if(clubList != null){
            adapter = ClubAdapter(clubList){

                position ->
                val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(clubList?.get(position)?.id ?: -1)//clubRepository.clubList[position])
                if (sharedPref != null) {
                    with (sharedPref.edit()) {
                        putInt("RecViewPos", position)
                        commit()
                    }
                }
                findNavController().navigate(action)

            }
        }
        recClubs.layoutManager = LinearLayoutManager(context)
        recClubs.adapter = adapter
        recClubs.scrollToPosition(prevPosition)

    }
}
