package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.android.ext.android.inject

class VoterInfoFragment : BaseFragment() {

    override val _viewModel: VoterInfoViewModel by inject()
    private lateinit var binding: FragmentVoterInfoBinding
    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voter_info, container, false)
        binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        _viewModel.getElectionInfo(args)

        _viewModel.votingLocationsUrl.observe(viewLifecycleOwner, Observer { url ->
            url?.let{
                loadUrl(url)
            }
        })

        _viewModel.ballotInformationUrl.observe(viewLifecycleOwner, Observer { url ->
            url?.let{
                loadUrl(url)
            }
        })

        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun loadUrl(url: String) {
        startActivity(Intent(ACTION_VIEW, Uri.parse(url)))
    }
}