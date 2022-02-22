package com.example.android.politicalpreparedness.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.politicalpreparedness.utils.SingleLiveEvent
import org.koin.core.KoinComponent

abstract class BaseViewModel(open val app: Application) : AndroidViewModel(app), KoinComponent {
    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
}
