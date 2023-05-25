package com.utn.firstapp.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.UserDetailViewModel
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User

class EditClubDetail : Fragment() {

    private val viewModel: EditClubDetailViewModel by viewModels()
    lateinit var v: View
    lateinit var btnUpdate: Button
    lateinit var txtName: EditText
    lateinit var txtFounded: EditText
    lateinit var txtCountry: EditText
    lateinit var txtNick: EditText
    lateinit var txtLeague: EditText
    lateinit var txtURL: EditText
    lateinit var txtNatFlag: EditText
    private var db: AppDatabase? = null
    private var clubDao: ClubDao? = null
    lateinit var getClub: Club


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_club_detail, container, false)

        btnUpdate = v.findViewById(R.id.btnUpdateEditClub)
        txtName = v.findViewById(R.id.edtEditClubTextName)
        txtFounded = v.findViewById(R.id.edtEditClubTextFounded)
        txtCountry = v.findViewById(R.id.edtEditClubTextCountry)
        txtLeague = v.findViewById(R.id.edtEditClubTextLeague)
        txtNick = v.findViewById(R.id.edtEditClubTextNick)
        txtURL = v.findViewById(R.id.edtEditClubTextImageURL)
        txtNatFlag = v.findViewById(R.id.edtEditClubNatFlag)


        return v
    }

    override fun onStart() {
        super.onStart()

        var clubID = EditClubDetailArgs.fromBundle(requireArguments()).clubID

        viewModel.getClubFromID(clubID)
        viewModel.team.observe(viewLifecycleOwner) { getClub ->

            txtName.setText(getClub.name)
            txtFounded.setText(getClub.founded)
            txtCountry.setText(getClub.country)
            txtLeague.setText(getClub.league)
            txtNick.setText(getClub.nickname)
            txtURL.setText(getClub.imageurl)
            txtNatFlag.setText(getClub.countryflag)
        }


        btnUpdate.setOnClickListener {

            try {
                if (clubID != null) {
                    val auxClub = Club(
                        clubID,
                        txtName.text.toString(),
                        txtFounded.text.toString(),
                        txtCountry.text.toString(),
                        txtNick.text.toString(),
                        txtURL.text.toString(),
                        txtLeague.text.toString(),
                        txtNatFlag.text.toString()
                    )

                    viewModel.updateClub(auxClub)
                }
                Snackbar.make(v, "Club data correctly updated", Snackbar.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Snackbar.make(v, "Wrong try editing club info.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}