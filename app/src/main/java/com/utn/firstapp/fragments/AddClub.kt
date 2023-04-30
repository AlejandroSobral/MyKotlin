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



        return v
    }
    override fun onStart() {
        super.onStart()


        var PassedClubRepo: ClubRepository = AddClubArgs.fromBundle(requireArguments()).ClubRepo

        btnAdd.setOnClickListener {
            try {
                var clubname: String = addClubName.text.toString()
                var clubcountry: String = addClubName.text.toString()
                var clubleague: String = addClubLeague.text.toString()
                var clubfounded: String = addClubFounded.text.toString()
                var clubnick: String = addClubNick.text.toString()

                val message = "Club, ${clubname}, has been added correctly!"
                PassedClubRepo.clubList.add(Club(clubname, clubfounded, clubcountry, clubnick, "https://www.vhv.rs/dpng/d/486-4867851_generic-football-club-logo-png-download-generic-football.png", clubleague, 0))
                Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                addClubCountry.setText("")
                addClubName.setText("")
                addClubLeague.setText("")
                addClubNick.setText("")
                addClubFounded.setText("")

            } catch (e: Exception) {
                Snackbar.make(v, "Wrong Club Parameters", Snackbar.LENGTH_SHORT).show()

            }



        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddClubViewModel::class.java)
        // TODO: Use the ViewModel
    }

}