package com.utn.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.firstapp.R
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


    var clubRepository: ClubRepository = ClubRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen2, container, false)
        //btnNavigate = v.findViewById(R.id.btnNav1)
        recClubs = v.findViewById(R.id.RecyView_Clubs)
        imgHomeLogo = v.findViewById(R.id.imgHomeLogo)
        Glide.with(v).load(imgHomeLogoURL).into(imgHomeLogo)




        return v
    }

    override fun onStart() {
        super.onStart()

        adapter = ClubAdapter(clubRepository.clubList){

            position ->
            val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(clubRepository.clubList[position])//clubRepository.clubList[position])
            findNavController().navigate(action)
            //findNavController().navigate(action)
        }
        recClubs.layoutManager = LinearLayoutManager(context)
        recClubs.adapter = adapter

        //btnNavigate.setOnClickListener {
            //findNavController().navigateUp()}



    }
}
