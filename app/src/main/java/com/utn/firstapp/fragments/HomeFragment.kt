package com.utn.firstapp.fragments

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import java.io.File
import java.util.Locale



@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 542
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: HomeFrgmtViewModel by viewModels()
    lateinit var loadingPb: ProgressBar
    lateinit var v: View
    lateinit var recClubs: RecyclerView
    lateinit var adapter: ClubAdapter
    var imgHomeLogoURL: String = "https://assets.stickpng.com/images/609912b13ae4510004af4a22.png"
    lateinit var imgHomeLogo: ImageView
    lateinit var locationFlag : ImageView
    lateinit var textFilter : AutoCompleteTextView

    private lateinit var switchLocFil: Switch
    lateinit var btnAddClub: Button
    lateinit var btnPic: Button

    private val sharedClubList: MutableLiveData<ClubRepository> = MutableLiveData()
    var clubRepository: ClubRepository = ClubRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen2, container, false)
        //btnNavigate = v.findViewById(R.id.btnNav1)
        recClubs = v.findViewById(R.id.RecyView_Clubs)
        imgHomeLogo = v.findViewById(R.id.imgHomeLogo)
        switchLocFil = v.findViewById(R.id.swtLocFilt)
        btnAddClub = v.findViewById(R.id.btnAddClub)
        loadingPb = v.findViewById(R.id.homeLoadingProgressBar)
        locationFlag = v.findViewById(R.id.locationCountryFlag)


        locationFlag.visibility = View.INVISIBLE

        val locationFlagURL = viewModel.getFlagByLocation()
        Glide.with(v).load(locationFlagURL).into(locationFlag)
        Glide.with(v).load(imgHomeLogoURL).into(imgHomeLogo)


        viewModel.stateLocation.observe(viewLifecycleOwner){state ->
            when (state) {
                State.SUCCESS -> {
                    locationFlag.visibility = View.VISIBLE
                    val locationName = viewModel.getNamedLocation()
                    //Snackbar.make(v, "You location has been set at $locationName properly.", Snackbar.LENGTH_SHORT).show()
                }
                State.FAILURE -> {

                }
                State.LOADING -> {

                }
                null -> {
                }

                else -> {}
            }
        }


        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.SUCCESS -> {
                    recClubs.visibility = View.VISIBLE
                    loadingPb.visibility = View.INVISIBLE


                    Log.d("PositionUser", "GetCurrentOK")
                    //var clubRepo = viewModel.getDataClubShPr()


                }

            State.FAILURE -> {}
            State.LOADING -> {
            recClubs.visibility = View.INVISIBLE
            loadingPb.visibility = View.VISIBLE

        }
            null -> {
        }

            else -> {}
        }
    }

        viewModel.teams?.observe(viewLifecycleOwner){ clubRepo ->
            Log.d("PositionUser", "ObserveClubRepo")
            var currentUser = viewModel.getUserfromPref()
            var adapterclubList = clubRepo?.clubList
            if (adapterclubList != null) {
                adapter = ClubAdapter(adapterclubList) { position ->
                    val action = HomeFragmentDirections.actionHomeFragmentToClubDDetail(
                        ((adapterclubList?.get(position)?.id ?: -1) as String)
                    )//as Int
                    currentUser?.lastposition = (position).toString()

                    if (currentUser != null) {
                        viewModel.updateUserPref(currentUser)
                    }
                    findNavController().navigate(action)

                }
            }

            recClubs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recClubs.layoutManager = LinearLayoutManager(context)
            recClubs.adapter = adapter

            if (currentUser != null) {
                Log.d("PositionUser", "Scroll to , ${currentUser.lastposition}")
                recClubs.scrollToPosition((currentUser.lastposition).toInt())
                loadingPb.visibility = View.GONE
            }
        }


    return v
}


override fun onStart() {
    super.onStart()

    switchLocFil.isChecked = false
    viewModel.mygetClubsFromDBCor()
        //Location
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    } else {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        viewModel.getLocation(fusedLocationClient, geocoder)
    }

    switchLocFil.setOnCheckedChangeListener { _, _ ->
        if (!switchLocFil.isChecked) {
            viewModel.mygetClubsFromDBCor()
            Snackbar.make(v, "Off filtering clubs by your current location.", Snackbar.LENGTH_SHORT).show()


        } else {
            viewModel.mygetClubsFromDBCorWithFilter()
            Snackbar.make(v, "Filtering clubs by your current location.", Snackbar.LENGTH_SHORT).show()
        }
    }





    btnAddClub.setOnClickListener {
        val action = HomeFragmentDirections.actionHomeFragmentToAddClub(clubRepository)
        findNavController().navigate(action)
    }

}
}