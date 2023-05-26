package com.utn.firstapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.State

class ClubDDetail : Fragment() {


    private val viewModel: ClubDDetailViewModel by viewModels()
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
    lateinit var btnDelete: Button
    lateinit var btnEdit: Button

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
        btnDelete  = v.findViewById(R.id.btnDetailDeleteClub)
        btnEdit = v.findViewById(R.id.btnDetailEdtClub)
        return v
    }

    override fun onStart(){
        super.onStart()
        db = AppDatabase.getInstance(v.context)

        clubdao = db?.clubDao()

        lateinit var getClub:Club

        var clubID  = ClubDDetailArgs.fromBundle(requireArguments()).clubID


        //getClub = clubdao?.fetchClubById(clubID) as Club
        // This getClub should come from DataBase, processed on ViewModel
        viewModel.getClubFromID(clubID)
        viewModel.team.observe(viewLifecycleOwner) { getClub ->

            txtName.text = getClub.name
            txtFounded.text = getClub.founded
            txtLeague.text = getClub.league
            txtNick.text = getClub.nickname
            txtCountry.text = getClub.country
            Glide.with(v).load(getClub.imageurl).into(imgClubDetail)
        }

        val navController = findNavController()

        btnDelete.setOnClickListener{
            try {

                viewModel.deleteClubFromID(clubID)

                viewModel.state.observe(this){state ->
                    when(state){
                        State.SUCCESS ->{

                            Snackbar.make(v, "Club has been deleted", Snackbar.LENGTH_SHORT).show()
                            navController.navigateUp()
                        }
                        State.FAILURE ->{
                            Snackbar.make(v, "Delete failed", Snackbar.LENGTH_SHORT).show()
                        }
                        State.LOADING ->{
                            Snackbar.make(v, "Loading", Snackbar.LENGTH_SHORT).show()
                        }
                        null ->{

                        }
                    }
                }

            }
            catch(e:Exception)
            {
                Snackbar.make(v, "Delete has failed.", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnEdit.setOnClickListener{


            //val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(
            val action = ClubDDetailDirections.actionClubDDetailToEditClubDetail(clubID)
            findNavController().navigate(action)


        }
    }



}