package com.utn.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.State

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
    lateinit var loadingPb: ProgressBar
    lateinit var htnName : TextView
    lateinit var htnFounDate: TextView
    lateinit var htnLeague: TextView
    lateinit var htnCountry : TextView
    lateinit var htnNick : TextView
    lateinit var htnImgURL : TextView
    lateinit var htnNatFlag : TextView

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
        loadingPb = v.findViewById(R.id.editClubLoadingProgressBar)
        htnName = v.findViewById(R.id.hntClubName)
        htnFounDate = v.findViewById(R.id.hntFounDate)
        htnLeague = v.findViewById(R.id.hntLeague)
        htnCountry = v.findViewById(R.id.hntCountry)
        htnNick = v.findViewById(R.id.hntNick)
        htnImgURL = v.findViewById(R.id.hntImageURL)
        htnNatFlag = v.findViewById(R.id.hntNationalFlagURL)


        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    //Texts
                    txtName.visibility = View.VISIBLE
                    txtFounded.visibility = View.VISIBLE
                    txtCountry.visibility = View.VISIBLE
                    txtLeague.visibility = View.VISIBLE
                    txtNick.visibility = View.VISIBLE
                    txtURL.visibility = View.VISIBLE
                    txtNatFlag.visibility = View.VISIBLE

                    //Hints
                    htnName.visibility = View.VISIBLE
                    htnFounDate.visibility = View.VISIBLE
                    htnLeague.visibility = View.VISIBLE
                    htnCountry.visibility = View.VISIBLE
                    htnNick.visibility = View.VISIBLE
                    htnImgURL.visibility = View.VISIBLE
                    htnNatFlag.visibility = View.VISIBLE

                    //ProgressBar
                    loadingPb.visibility = View.GONE
                    Snackbar.make(v, "Club data correctly updated", Snackbar.LENGTH_SHORT).show()
                }

                State.FAILURE -> {
                    Snackbar.make(v, "Wrong try editing club info.", Snackbar.LENGTH_SHORT
                    ).show()
                }

                State.LOADING -> {
                    //Texts
                    loadingPb.visibility = View.VISIBLE
                    txtName.visibility = View.INVISIBLE
                    txtFounded.visibility = View.INVISIBLE
                    txtCountry.visibility = View.INVISIBLE
                    txtLeague.visibility = View.INVISIBLE
                    txtNick.visibility = View.INVISIBLE
                    txtURL.visibility = View.INVISIBLE
                    txtNatFlag.visibility = View.INVISIBLE

                    //Hints
                    htnName.visibility = View.INVISIBLE
                    htnFounDate.visibility = View.INVISIBLE
                    htnLeague.visibility = View.INVISIBLE
                    htnCountry.visibility = View.INVISIBLE
                    htnNick.visibility = View.INVISIBLE
                    htnImgURL.visibility = View.INVISIBLE
                    htnNatFlag.visibility = View.INVISIBLE
                }

                null -> {}
                else -> {}
            }
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        //ProgressBar
        loadingPb.visibility = View.VISIBLE
        //Texts
        txtName.visibility = View.INVISIBLE
        txtFounded.visibility = View.INVISIBLE
        txtCountry.visibility = View.INVISIBLE
        txtLeague.visibility = View.INVISIBLE
        txtNick.visibility = View.INVISIBLE
        txtURL.visibility = View.INVISIBLE
        txtNatFlag.visibility = View.INVISIBLE
        //Hints
        htnName.visibility = View.INVISIBLE
        htnFounDate.visibility = View.INVISIBLE
        htnLeague.visibility = View.INVISIBLE
        htnCountry.visibility = View.INVISIBLE
        htnNick.visibility = View.INVISIBLE
        htnImgURL.visibility = View.INVISIBLE
        htnNatFlag.visibility = View.INVISIBLE

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

            //Hints
            htnName.visibility = View.VISIBLE
            htnFounDate.visibility = View.VISIBLE
            htnLeague.visibility = View.VISIBLE
            htnCountry.visibility = View.VISIBLE
            htnNick.visibility = View.VISIBLE
            htnImgURL.visibility = View.VISIBLE
            htnNatFlag.visibility = View.VISIBLE
            //Texts
            txtName.visibility = View.VISIBLE
            txtFounded.visibility = View.VISIBLE
            txtCountry.visibility = View.VISIBLE
            txtLeague.visibility = View.VISIBLE
            txtNick.visibility = View.VISIBLE
            txtURL.visibility = View.VISIBLE
            txtNatFlag.visibility = View.VISIBLE
            //ProgressBar
            loadingPb.visibility = View.GONE

        }

        btnUpdate.setOnClickListener {

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

                viewModel.myUpdateClubCor(auxClub)

            }
        }
    }
}


