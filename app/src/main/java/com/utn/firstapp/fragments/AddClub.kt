package com.utn.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository

class AddClub : Fragment() {

    lateinit var v: View
    lateinit var btnAdd: Button
    lateinit var addClubName : EditText
    lateinit var addClubFounded : EditText
    lateinit var addClubLeague : EditText
    lateinit var addClubCountry : EditText
    lateinit var addClubNick : EditText
    lateinit var addClubURL : EditText
    private var db: AppDatabase? = null
    private var clubDao: ClubDao? = null



    private lateinit var viewModel: AddClubViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_add_club, container, false)
        btnAdd = v.findViewById(R.id.btnClubAdd)
        addClubName = v.findViewById(R.id.edtTxtAddClubName)
        addClubFounded = v.findViewById(R.id.edtTxtAddClubFounded)
        addClubCountry = v.findViewById(R.id.edtTxtAddClubCountry)
        addClubLeague = v.findViewById(R.id.edtTxtAddClubLeague)
        addClubNick = v.findViewById(R.id.edtTxtAddClubNick)
        addClubURL = v.findViewById(R.id.edtTxtAddClubURL)

        return v
    }
    override fun onStart() {
        super.onStart()

        db = AppDatabase.getInstance(v.context)
        clubDao = db?.clubDao()
        var newClub =  Club("0","","","","","", "", "")

        //var PassedClubRepo: ClubRepository = AddClubArgs.fromBundle(requireArguments()).ClubRepo

        btnAdd.setOnClickListener {
            try {
                var clubname: String = addClubName.text.toString()
                var clubcountry: String = addClubCountry.text.toString()
                var clubleague: String = addClubLeague.text.toString()
                var clubfounded: String = addClubFounded.text.toString()
                var clubnick: String = addClubNick.text.toString()
                var imageURL: String = addClubURL.text.toString()

                if(imageURL == ""){
                    imageURL = "https://www.vhv.rs/dpng/d/486-4867851_generic-football-club-logo-png-download-generic-football.png"
                }

                newClub.name = clubname
                newClub.country = clubcountry
                newClub.league = clubleague
                newClub.founded = clubfounded
                newClub.nickname = clubnick
                newClub.imageURL = imageURL

                //PassedClubRepo.clubList.add(Club(clubname, clubfounded, clubcountry, clubnick, "https://www.vhv.rs/dpng/d/486-4867851_generic-football-club-logo-png-download-generic-football.png", clubleague, 0))

                addClubCountry.setText("")
                addClubName.setText("")
                addClubLeague.setText("")
                addClubNick.setText("")
                addClubFounded.setText("")
                addClubURL.setText("")


                if(clubDao?.fetchClubByName(newClub.name)==null)
                {
                    clubDao?.insertClub(newClub)
                    val message = "Club, ${clubname}, has been added correctly!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                }

                else
                {
                    val message = "Club, ${clubname}, has already exists!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                }



            }
            catch (e: Exception) {
                Snackbar.make(v, "Wrong Club insert.", Snackbar.LENGTH_SHORT).show()

            }

        }

    }

}