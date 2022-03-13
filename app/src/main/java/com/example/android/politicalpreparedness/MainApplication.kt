package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.repository.CivicRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application()  {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                ElectionsViewModel(
                    get()
                )
            }

            viewModel {
                VoterInfoViewModel(
                    get(),
                    get() as CivicRepository
                )
            }

            single { CivicRepository(get())}
            single { ElectionDatabase.getInstance(this@MainApplication) }


//            viewModel {
//                RemindersListViewModel(
//                    get(),
//                    get() as ReminderDataSource
//                )
//            }
//            //Declare singleton definitions to be later injected using by inject()
//            single {
//                //This view model is declared singleton to be used across multiple fragments
//                SaveReminderViewModel(
//                    get(),
//                    get() as ReminderDataSource
//                )
//            }
//            single { RemindersLocalRepository(get()) as ReminderDataSource }
//            single { LocalDB.createRemindersDao(this@MyApp) }
//            single { FirebaseUserLiveData() as LiveData<FirebaseUser?> }

        }

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(myModule))
        }
    }


}