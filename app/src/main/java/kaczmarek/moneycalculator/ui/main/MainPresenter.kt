package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.presenterScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

class MainPresenter : PresenterBase<MainView>() {
    @Inject
    lateinit var interactor: InteractorMain

    init {
        DIManager.getMainSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeMainSubcomponent()
    }

    override fun onFirstViewAttach() {
        viewState.onFirstOpen()
        when (interactor.getHistoryStoragePeriod()) {
            SettingsService.FOURTEEN_DAYS -> deleteSessionsFor(14)
            SettingsService.THIRTY_DAYS -> deleteSessionsFor(30)
        }
    }


    private fun deleteSessionsFor(days: Int) {
        presenterScope.launch {
            try {
                val stringDeleteDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        Calendar.getInstance().run {
                            add(Calendar.DAY_OF_MONTH, -days)
                            time
                        }
                    )
                interactor.deleteSession(interactor.getAll().first {
                    it.date <= stringDeleteDate
                })
            } catch (e: Exception) {
                viewState.showMessage(getString(R.string.common_delete_error, e.toString()))
            }
        }
    }
}