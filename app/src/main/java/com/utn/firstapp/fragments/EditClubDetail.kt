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
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.UserDetailViewModel
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User

class EditClubDetail : Fragment() {

    lateinit var v: View
    lateinit var btnUpdate : Button
    lateinit var txtName: EditText
    lateinit var txtFounded: EditText
    lateinit var txtCountry: EditText
    lateinit var txtNick: EditText
    lateinit var txtLeague : EditText
    lateinit var txtURL : EditText
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


        return v
    }
    override fun onStart(){
        super.onStart()



        db = AppDatabase.getInstance(v.context)
        clubDao = db?.clubDao()


        val sharedPref = context?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val idClub = sharedPref!!.getInt("ClubID", 0)

        getClub = clubDao?.fetchClubById(idClub) as Club

        txtName.setText(getClub.name)
        txtFounded.setText(getClub.founded)
        txtCountry.setText(getClub.country)
        txtLeague.setText(getClub.league)
        txtNick.setText(getClub.nickname)
        txtURL.setText(getClub.imageURL)


        btnUpdate.setOnClickListener{
        try {
            if (idClub != null) {
                val clubname = txtName.text.toString()
                val founded = txtFounded.text.toString()
                val country = txtCountry.text.toString()
                val league = txtLeague.text.toString()
                val nick = txtNick.text.toString()
                val url = txtURL.text.toString()

                getClub.name = clubname
                getClub.founded = founded
                getClub.country = country
                getClub.league = league
                getClub.nickname = nick
                getClub.imageURL = url

                clubDao?.updateClub(getClub)
                Snackbar.make(v, "Club data correctly updated", Snackbar.LENGTH_SHORT).show()


            }
        }
        catch(e:Exception)
        {
            Snackbar.make(v, "Wrong try editing club info.", Snackbar.LENGTH_SHORT).show()
        }
        }
    }


}