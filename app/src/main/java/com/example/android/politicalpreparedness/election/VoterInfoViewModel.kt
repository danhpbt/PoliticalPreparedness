package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseViewModel
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.CivicRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(application: Application,
                         private val repository: CivicRepository) : BaseViewModel(application) {

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse> = _voterInfo

//    private val _networkError = MutableLiveData<Boolean>()
//    val networkError: LiveData<Boolean> = _networkError
//
//    private val _hasVoterInfo = MutableLiveData<Boolean>()
//    val hasVoterInfo: LiveData<Boolean> = _hasVoterInfo

    private val _correspondenceAddress = MutableLiveData<String>()
    val correspondenceAddress: LiveData<String> = _correspondenceAddress

    private val _hasCorrespondenceAddress = MutableLiveData<Boolean>()
    val hasCorrespondenceAddress: LiveData<Boolean> = _hasCorrespondenceAddress

    private val _isElectionFollowed = MutableLiveData<Boolean>()
    val isElectionFollowed: LiveData<Boolean> = _isElectionFollowed

    private val _votingLocationsUrl = MutableLiveData<String>()
    val votingLocationsUrl: LiveData<String> = _votingLocationsUrl

    private val _ballotInformationUrl = MutableLiveData<String>()
    val ballotInformationUrl: LiveData<String> = _ballotInformationUrl

    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean> = _hasError

    private val _errorInfo = MutableLiveData<String>()
    val errorInfo: LiveData<String> = _errorInfo

    fun getElectionInfo(navArgs: VoterInfoFragmentArgs)
    {
        populateVoterInfo(navArgs)
        //checkFollowingElection(navArgs)
    }

    //TODO: Add var and methods to populate voter info
    private fun populateVoterInfo(navArgs: VoterInfoFragmentArgs) {
        val country = navArgs.argDivision.country
        val state = navArgs.argDivision.state

        if (state.isNotEmpty()) {
            showLoading.value = true

            viewModelScope.launch {
                val electionId = navArgs.argElectionId
                val address = "$country,$state"
                val result = repository.getVoterInfo(electionId, address)
                if (result != null) {
                    _voterInfo.value = result
                    getCorrespondenceAddress()
                    checkFollowingElection(navArgs)
                    _hasError.value = true
                } else {
                    _hasError.value = true
                    showSnackBarInt.value = R.string.str_check_connection
                    _errorInfo.value = getString(R.string.str_network_error)
                }
            }

            showLoading.value = false
        } else {
            _hasError.value = true
            _errorInfo.value = getString(R.string.str_no_data)
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    private fun getCorrespondenceAddress() {
        _correspondenceAddress.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
        _hasCorrespondenceAddress.value = _correspondenceAddress.value != null
    }

    fun getAddress(division: Division): String {
        if (division.state.isNotEmpty()) {
            return "${division.country},${division.state}"
        }
        return "USA,WA"
    }

    fun loadVotingLocations() {
        _votingLocationsUrl.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl
    }

    fun loadBallotInformation() {
        _ballotInformationUrl.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
    }

    private fun checkFollowingElection(navArgs: VoterInfoFragmentArgs) {
        viewModelScope.launch {
            _isElectionFollowed.value = repository.isElectionFollowed(navArgs.argElectionId)
        }
    }


}