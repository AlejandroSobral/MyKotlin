package com.utn.firstapp.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.firstapp.R
import com.utn.firstapp.activities.MainActivity
import com.utn.firstapp.adapters.ClubAdapter
import com.utn.firstapp.database.AppDatabase
import com.utn.firstapp.database.ClubDao
import com.utn.firstapp.database.UserDao
import com.utn.firstapp.entities.ClubRepository
import com.utn.firstapp.entities.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeFrgmtViewModel by viewModels()
    lateinit var loadingPb: ProgressBar
    lateinit var v: View
    lateinit var recClubs: RecyclerView
    lateinit var adapter: ClubAdapter
    var imgHomeLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"
    lateinit var imgHomeLogo: ImageView
    lateinit var btnLogOut: Button
    lateinit var btnAddClub: Button


    var clubRepository: ClubRepository = ClubRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen2, container, false)
        //btnNavigate = v.findViewById(R.id.btnNav1)
        recClubs = v.findViewById(R.id.RecyView_Clubs)
        imgHomeLogo = v.findViewById(R.id.imgHomeLogo)
        btnLogOut = v.findViewById(R.id.btnLogOut)
        btnAddClub = v.findViewById(R.id.btnAddClub)
        loadingPb = v.findViewById(R.id.homeLoadingProgressBar)
        Glide.with(v).load(imgHomeLogoURL).into(imgHomeLogo)

        return v
    }


    override fun onStart() {
        super.onStart()

        loadingPb.visibility = View.VISIBLE
        var currentUser = viewModel.getUser()

        if (currentUser != null) {
            Log.d("PositionUser", "L81, ${currentUser.lastposition}")
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                State.SUCCESS -> {
                    Log.d("PositionUser", "GetCurrentOK")
                }

                State.FAILURE -> {}
                State.LOADING -> {

                }
                null -> {
                }
            }
        }


        viewModel.getClubsFromDB()
        viewModel.teams.observe(viewLifecycleOwner) { getClubList ->

            var adapterclubList = getClubList.clubList
            if (adapterclubList != null) {
                adapter = ClubAdapter(adapterclubList) { position ->
                    val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(
                        ((adapterclubList?.get(position)?.id ?: -1) as String)
                    )//as Int


                    currentUser?.lastposition = (position).toString()
                    if (currentUser != null) {
                        Log.d("PositionUser", "L124, ${currentUser.lastposition}")
                        Log.d("PositionUser", "Position, $position")
                    }


                    if (currentUser != null) {
                        viewModel.updateUser(currentUser)
                        Log.d("PositionUser", "Guardo Position, ${currentUser.lastposition}")

                    }
                    findNavController().navigate(action)

                }
            }
            recClubs.layoutManager = LinearLayoutManager(context)
            recClubs.adapter = adapter
            if (currentUser != null) {
                Log.d("PositionUser", "Scroll to , ${currentUser.lastposition}")
                recClubs.scrollToPosition((currentUser.lastposition).toInt())
                loadingPb.visibility = View.GONE
            }

        }

        btnLogOut.setOnClickListener {
            val context = requireContext()
            val builder = AlertDialog.Builder(context)

            // set the message and title of the dialog
            builder.setMessage("Are you sure you want to log out?")
                .setTitle("Logout")
            // add buttons to the dialog
            builder.setPositiveButton("Accept",
                DialogInterface.OnClickListener { dialog, id ->
                    // user clicked Accept button
                    // do your logout logic here
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("fragmentId", R.id.LoginFragment)
                    startActivity(intent)

                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    // user cancelled the dialog
                    dialog.dismiss()
                })

            // create and show the dialog
            val dialog = builder.create()
            dialog.show()
        }
        btnAddClub.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddClub(clubRepository)
            findNavController().navigate(action)
        }
    }
}
