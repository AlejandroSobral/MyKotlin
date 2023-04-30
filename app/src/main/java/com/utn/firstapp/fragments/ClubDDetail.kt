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
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User

class ClubDDetail : Fragment() {

    lateinit var v: View
    lateinit var txtNick: TextView
    lateinit var txtName: TextView
    lateinit var txtFounded: TextView
    lateinit var txtLeague: TextView
    lateinit var txtCountry: TextView
    lateinit var imgClubDetail : ImageView
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var clubdao: ClubDao? = null

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
        db = AppDatabase.getInstance(v.context)

        clubdao = db?.clubDao()

        lateinit var getClub:Club

        var clubID  = ClubDDetailArgs.fromBundle(requireArguments()).clubID
        getClub = clubdao?.fetchClubById(clubID) as Club


        txtName.text = getClub.name
        txtFounded.text = getClub.founded
        txtLeague.text = getClub.league
        txtNick.text = getClub.nickname
        txtCountry.text = getClub.country
        Glide.with(v).load(getClub.imageURL).into(imgClubDetail)


    }



}