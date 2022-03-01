package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import org.koin.android.ext.android.inject

class ElectionsFragment: BaseFragment() {

    //TODO: Declare ViewModel
    override val _viewModel: ElectionsViewModel by inject()
    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel


        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)
        binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        var upcomingElectionAdapter = ElectionListAdapter(upcomingElectionListener)
        var followedElectionAdapter = ElectionListAdapter(followedElectionListener)

        binding.rvElections.adapter = upcomingElectionAdapter
        binding.rvSavedElections.adapter = followedElectionAdapter

        _viewModel.upcomingElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                upcomingElectionAdapter.submitList(it)
            }
        })
        _viewModel.followedElections.observe(viewLifecycleOwner, Observer {
            it?.let{
                followedElectionAdapter.submitList(it)
            }
        })

        return binding.root

    }

    val upcomingElectionListener = ElectionListener{it ->
        findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                it.id,
                it.division
            )
        )
    }

    val followedElectionListener = ElectionListener{ it ->
        findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                it.id,
                it.division
            )
        )
    }

    //TODO: Refresh adapters when fragment loads

}