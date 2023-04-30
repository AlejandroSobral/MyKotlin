package com.utn.firstapp.fragments

import android.content.Intent
import android.os.Bundle
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
import com.utn.firstapp.entities.ClubRepository




class HomeFragment : Fragment() {


    lateinit var label: TextView
    //lateinit var btnNavigate: Button
    lateinit var v: View
    lateinit var recClubs: RecyclerView
    lateinit var adapter: ClubAdapter
    var imgHomeLogoURL: String = "https://upload.wikimedia.org/wikipedia/commons/8/85/Logo_lpf_afa.png"
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

        val idUser = activity?.intent?.getIntExtra("idUser", -1)
        btnLogOut.setOnClickListener{

            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("fragmentId", R.id.LoginFragment)
            startActivity(intent)
        }

        btnAddClub.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddClub(clubRepository)
            findNavController().navigate(action)
        }


        adapter = ClubAdapter(clubRepository.clubList){

            position ->
            val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(clubRepository.clubList[position])//clubRepository.clubList[position])
            findNavController().navigate(action)
            //findNavController().navigate(action)
        }
        recClubs.layoutManager = LinearLayoutManager(context)
        recClubs.adapter = adapter

    }
}
