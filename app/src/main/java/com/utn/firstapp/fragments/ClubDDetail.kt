package com.utn.firstapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.utn.firstapp.R
import com.utn.firstapp.entities.Club

class ClubDDetail : Fragment() {

    lateinit var v: View
    lateinit var txtNick: TextView
    lateinit var txtName: TextView
    lateinit var txtFounded: TextView
    lateinit var txtLeague: TextView
    lateinit var txtCountry: TextView
    lateinit var imgClubDetail : ImageView

        //Club, Country, League, Founded, Nick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_club_d_detail, container, false)
        txtName = v.findViewById(R.id.txtDetailClubName)
        txtFounded = v.findViewById(R.id.txtDetailClubFounded)
        txtLeague = v.findViewById(R.id.txtDetailClubLeague)
        txtNick = v.findViewById(R.id.txtDetailClubNick)
        txtCountry = v.findViewById(R.id.txtDetailClubCountry)
        imgClubDetail = v.findViewById(R.id.imgDetailClub)
        return v
    }

    override fun onStart(){
        super.onStart()
        var getclub : Club = ClubDDetailArgs.fromBundle(requireArguments()).passedclub

        txtName.text = getclub.name
        txtFounded.text = getclub.founded
        txtLeague.text = getclub.league
        txtNick.text = getclub.nickname
        txtCountry.text = getclub.country
        Glide.with(v).load(getclub.imageURL).into(imgClubDetail)


    }



}