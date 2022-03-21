package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.BuildConfig
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.Locale

class DetailFragment : BaseFragment() {

    companion object {
        //TODO: Add Constant for Location request
        private const val REQUEST_ACCESS_FINE_LOCATION = 9879

    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    //TODO: Declare ViewModel
    override val _viewModel: RepresentativeViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        //TODO: Establish bindings
        val binding = FragmentRepresentativeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        //TODO: Define and assign Representative adapter
        val representativeListAdapter = RepresentativeListAdapter()
        binding.rvRepresentatives.adapter = representativeListAdapter

        //TODO: Populate Representative adapter
        _viewModel.representatives.observe(viewLifecycleOwner, Observer {
            it?.let {
                representativeListAdapter.submitList(it)
            }
        })

        //TODO: Establish button listeners for field and location search
        binding.buttonSearch.setOnClickListener {
            hideKeyboard()
            _viewModel.getRepresentatives()
        }

        binding.buttonLocation.setOnClickListener {
            if (isPermissionGranted()) {
                getLocation()
            } else {
                requestLocationPermission()
            }
        }


        binding.state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                _viewModel.address.value?.state = binding.state.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                _viewModel.address.value?.state = binding.state.selectedItem.toString()
            }

        }

        return binding.root

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //TODO: Handle location permission result to get location on permission granted
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getLocation()
        }
        else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(this.view!!, R.string.str_permission_denied, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok) {
                        Timber.d("User denied location permission")
                    }.show()
            }
        }
    }

    private fun isPermissionGranted() : Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        if (isPermissionGranted()) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val address = geoCodeLocation(location)
                    _viewModel.updateAddress(address)
                }
            }
        } else {
            requestLocationPermission()
        }

    }

    private fun requestLocationPermission() {
        val permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = REQUEST_ACCESS_FINE_LOCATION

        requestPermissions(
            permissionsArray,
            resultCode
        )
    }


    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare,
                        address.subThoroughfare,
                        address.locality,
                        address.adminArea,
                        address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}