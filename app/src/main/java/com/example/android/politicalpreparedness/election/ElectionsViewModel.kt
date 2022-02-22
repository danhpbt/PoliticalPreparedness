package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.base.BaseViewModel
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.CivicRepository
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application): BaseViewModel(application) {

    private val database = ElectionDatabase.getInstance(application)
    private val repository = CivicRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshElections()
        }
    }

    val upcomingElections = repository.elections
    val followedElections = repository.followedElections
}