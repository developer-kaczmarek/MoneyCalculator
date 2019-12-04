package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.database.models.Session
import kaczmarek.moneycalculator.ui.base.BaseItem
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
@InjectViewState
class PresenterHistory : PresenterBase<ViewHistory>() {
    @Inject
    lateinit var interactor: InteractorHistory
    lateinit var groupSessions: Map<String, List<Session>>
    val allHistoryItems = arrayListOf<BaseItem>()

    init {
        DIManager.getHistorySubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeHistorySubcomponent()
    }

    fun getSessions() {
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