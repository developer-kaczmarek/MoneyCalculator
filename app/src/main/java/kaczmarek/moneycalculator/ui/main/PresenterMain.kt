package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import moxy.InjectViewState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@InjectViewState
class PresenterMain : PresenterBase<ViewMain>() {
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
        val stringDeleteDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                Calendar.getInstance().run {
                    add(Calendar.DAY_OF_MONTH, -days)
                    time
                }
            )

        try {
            interactor.getAll().forEach {
                if (it.date <= stringDeleteDate) {
                    interactor.deleteSession(it)
                }
            }
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_delete_error,
                    e.toString()
                )
            )
        }

    }
}