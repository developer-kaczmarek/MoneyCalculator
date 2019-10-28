package com.kaczmarek.moneycalculator.ui.history.presenters

import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.FOURTEEN_DAYS
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.INDEFINITELY
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.THIRTY_DAYS
import com.kaczmarek.moneycalculator.di.services.database.models.Session
import com.kaczmarek.moneycalculator.ui.base.adapters.BaseItem
import com.kaczmarek.moneycalculator.ui.base.presenters.BasePresenter
import com.kaczmarek.moneycalculator.ui.history.interactors.HistoryInteractor
import com.kaczmarek.moneycalculator.ui.history.models.DateItem
import com.kaczmarek.moneycalculator.ui.history.models.SessionItem
import com.kaczmarek.moneycalculator.ui.history.views.HistoryView
import com.kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
@InjectViewState
class HistoryPresenter : BasePresenter<HistoryView>() {
    @Inject
    lateinit var interactor: HistoryInteractor
    lateinit var groupSessions: Map<String, List<Session>>
    val allHistoryItems = arrayListOf<BaseItem>()

    init {
        DIManager.getHistorySubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeHistorySubcomponent()
    }

    fun getKeyboardLayout() {
        when (interactor.getHistoryStoragePeriod()) {
            INDEFINITELY -> getSessions()
            FOURTEEN_DAYS -> deleteSessionsFor(14)
            THIRTY_DAYS -> deleteSessionsFor(30)
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
        launch {
            try {
                interactor.getAll().forEach {
                    if (it.date <= stringDeleteDate) {
                        interactor.deleteSession(it)
                    }
                }
                getSessions()
            } catch (e: Exception) {
                viewState.showMessage(
                    getString(
                        R.string.common_load_error,
                        e.toString()
                    )
                )
            }
        }

    }

    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()

    private fun getSessions() {
        launch {
            try {
                allHistoryItems.clear()
                groupSessions = interactor.getAll().reversed().groupBy { it.date }
                groupSessions.forEach { session ->
                    allHistoryItems.add(DateItem(session.key))
                    session.value.sortedBy { it.time }.forEach {
                        allHistoryItems.add(SessionItem(it))
                    }
                }
                viewState.updateSessions()
            } catch (e: Exception) {
                viewState.showMessage(
                    getString(
                        R.string.common_load_error,
                        e.toString()
                    )
                )
            }
        }
    }
}