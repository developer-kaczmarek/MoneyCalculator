package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.FOURTEEN_DAYS
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.THIRTY_DAYS
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionsByDateUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetHistoryStoragePeriodUseCase
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.logError
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
    lateinit var deleteSessionsByDateUseCase: DeleteSessionsByDateUseCase

    @Inject
    lateinit var getHistoryStoragePeriodUseCase: GetHistoryStoragePeriodUseCase

    init {
        DIManager.getMainSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeMainSubcomponent()
    }

    override fun onFirstViewAttach() {
        viewState.onFirstOpen()
        when (getHistoryStoragePeriodUseCase.invoke()) {
            FOURTEEN_DAYS -> deleteSessionsFor(14)
            THIRTY_DAYS -> deleteSessionsFor(30)
        }
    }


    private fun deleteSessionsFor(days: Int) {
        presenterScope.launch {
            try {
                deleteSessionsByDateUseCase.invoke(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        Calendar.getInstance().run {
                            add(Calendar.DAY_OF_MONTH, -days)
                            time
                        }
                    )
                )
            } catch (e: Exception) {
                logError(TAG, e.message)
                viewState.showMessage(getString(R.string.common_delete_error, e.toString()))
            }
        }
    }

    companion object {
        const val TAG = "MainPresenter"
    }
}