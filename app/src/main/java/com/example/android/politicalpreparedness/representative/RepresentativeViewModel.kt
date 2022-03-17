package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.base.BaseViewModel
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(application: Application): BaseViewModel(application) {

    //TODO: Establish live data for representatives and address
    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> = _address

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    //TODO: Create function to fetch representatives from API from a provided address
    fun getRepresentatives() {
        viewModelScope.launch {
            address.value?.let {
                val (offices, officials) = CivicsApi.retrofitService.getRepresentatives(it.toFormattedString())
                _representatives.value = offices.flatMap { office ->  office.getRepresentatives(officials)}
            }
        }
    }
    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun updateAddress(address: Address){
        _address.value = address
        getRepresentatives()
    }

    //TODO: Create function to get address from individual fields

}
