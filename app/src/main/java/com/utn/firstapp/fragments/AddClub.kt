package com.utn.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.utn.firstapp.R
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State

class AddClub : Fragment() {

    lateinit var v: View
    lateinit var btnAdd: Button
    lateinit var addClubName: TextInputLayout
    lateinit var addClubFounded: TextInputLayout
    lateinit var addClubLeague: TextInputLayout
    lateinit var addClubCountry: TextInputLayout
    lateinit var addClubNick: TextInputLayout
    lateinit var addClubURL: TextInputLayout
    lateinit var loadingPb: ProgressBar
    lateinit var addClubCountryFlag: TextInputLayout
    private var db: AppDatabase? = null
    private var clubDao: ClubDao? = null
    private val viewModel: AddClubViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_add_club, container, false)
        btnAdd = v.findViewById(R.id.btnClubAdd)
        addClubName = v.findViewById(R.id.edtAddClubName)
        addClubFounded = v.findViewById(R.id.edtAddFoundationDate)
        addClubCountry = v.findViewById(R.id.edtAddCountry)
        addClubLeague = v.findViewById(R.id.edtAddLeague)
        addClubNick = v.findViewById(R.id.edtAddNick)
        addClubURL = v.findViewById(R.id.edtAddImageURL)
        addClubCountryFlag = v.findViewById(R.id.edtAddCountryFlagURL)
        loadingPb = v.findViewById(R.id.loadingPb)
        loadingPb.visibility = View.INVISIBLE

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    Snackbar.make(v, "Club has been added", Snackbar.LENGTH_SHORT).show()
                    loadingPb.visibility = View.INVISIBLE
                    addClubName.visibility = View.VISIBLE
                    addClubFounded.visibility = View.VISIBLE
                    addClubCountry.visibility = View.VISIBLE
                    addClubNick.visibility = View.VISIBLE
                    addClubURL.visibility = View.VISIBLE
                    addClubLeague.visibility = View.VISIBLE
                    addClubCountryFlag.visibility = View.VISIBLE

                }

                State.FAILURE -> {
                    Snackbar.make(v, "Club Add failed", Snackbar.LENGTH_SHORT).show()
                }

                State.LOADING -> {
                    Snackbar.make(v, "Loading", Snackbar.LENGTH_SHORT).show()
                    loadingPb.visibility = View.VISIBLE
                    addClubName.visibility = View.INVISIBLE
                    addClubFounded.visibility = View.INVISIBLE
                    addClubCountry.visibility = View.INVISIBLE
                    addClubNick.visibility = View.INVISIBLE
                    addClubURL.visibility = View.INVISIBLE
                    addClubLeague.visibility = View.INVISIBLE
                    addClubCountryFlag.visibility = View.INVISIBLE
                }

                null -> {
                }

                else -> {}
            }
        }

        return v
    }

    override fun onStart() {
        super.onStart()



        btnAdd.setOnClickListener {

                var newClub = Club(
                    "0",
                    addClubName.editText?.text.toString(),
                    addClubFounded.editText?.text.toString(),
                    addClubCountry.editText?.text.toString(),
                    addClubNick.editText?.text.toString(),
                    addClubURL.editText?.text.toString(),
                    addClubLeague.editText?.text.toString(),
                    addClubCountryFlag.editText?.text.toString()
                )


                if (newClub.imageurl == "") {
                    newClub.imageurl =
                        "https://www.vhv.rs/dpng/d/486-4867851_generic-football-club-logo-png-download-generic-football.png"
                }
                if (newClub.countryflag == "") {
                    newClub.countryflag =
                        "https://cdn-icons-png.flaticon.com/512/149/149263.png"
                }


                addClubCountry.editText?.setText("")
                addClubName.editText?.setText("")
                addClubLeague.editText?.setText("")
                addClubNick.editText?.setText("")
                addClubFounded.editText?.setText("")
                addClubURL.editText?.setText("")
                addClubCountryFlag.editText?.setText("")

                viewModel.myAddClub(newClub)

        }

    }

}