package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)

        binding.viewModel = _viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    //TODO: Refresh adapters when fragment loads

}