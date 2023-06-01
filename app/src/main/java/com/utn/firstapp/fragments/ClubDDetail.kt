package com.utn.firstapp.fragments


import android.media.tv.TvContract.Programs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
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
    lateinit var btnDelete: Button
    lateinit var btnEdit: Button
    lateinit var loadingBar : ProgressBar

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
        loadingBar = v.findViewById(R.id.clubDetailLoadingProgressBar)

        viewModel.team.observe(viewLifecycleOwner) { getClub ->

            if(getClub != null) {

                txtName.text = getClub.name
                txtFounded.text = getClub.founded
                txtLeague.text = getClub.league
                txtNick.text = getClub.nickname
                txtCountry.text = getClub.country
                //Visibility
                txtName.visibility = View.VISIBLE
                txtFounded.visibility = View.VISIBLE
                txtLeague.visibility = View.VISIBLE
                txtNick.visibility = View.VISIBLE
                txtCountry.visibility = View.VISIBLE
                loadingBar.visibility = View.GONE

                Glide.with(v).load(getClub.imageurl).into(imgClubDetail)
            }
        }
        val navController = findNavController()
        viewModel.state.observe(viewLifecycleOwner){state ->
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

                else -> {}
            }
        }

        return v
    }

    override fun onStart(){
        super.onStart()

        loadingBar.visibility = View.VISIBLE
        txtName.visibility = View.GONE
        txtFounded.visibility = View.GONE
        txtLeague.visibility = View.GONE
        txtNick.visibility = View.GONE
        txtCountry.visibility = View.GONE
        var clubID  = ClubDDetailArgs.fromBundle(requireArguments()).clubID

        viewModel.getClubFromID(clubID)


        btnDelete.setOnClickListener{

                viewModel.deleteClubFromID(clubID)
        }
        btnEdit.setOnClickListener{

            //val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(
            val action = ClubDDetailDirections.actionClubDDetailToEditClubDetail(clubID)
            findNavController().navigate(action)
        }
    }



}