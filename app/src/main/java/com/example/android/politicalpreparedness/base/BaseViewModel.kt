package com.example.android.politicalpreparedness.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.utils.SingleLiveEvent
import org.koin.core.KoinComponent

abstract class BaseViewModel(open val app: Application) : AndroidViewModel(app), KoinComponent {
    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showNoData: MutableLiveData<Boolean> = MutableLiveData()

    fun getString(id : Int) : String{
        return app.resources.getString(id)
    }
}
