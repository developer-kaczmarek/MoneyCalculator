package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.database.models.Session
import kaczmarek.moneycalculator.ui.base.BaseItem
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
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
    private var recentlyRemovedItem: Pair<Int, SessionItem>? = null

    init {
        DIManager.getHistorySubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeHistorySubcomponent()
    }

    fun getSessions() {
        try {
            allHistoryItems.clear()
            groupSessions = interactor.getAll().reversed().groupBy { it.date }
            groupSessions.forEach { session ->
                allHistoryItems.add(DateItem(session.key))
                session.value.sortedByDescending { it.time }.forEach {
                    allHistoryItems.add(SessionItem(it, false))
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

    fun deleteItemFromList(position: Int) {
        try {
            if (allHistoryItems.isNotEmpty()) {
                recentlyRemovedItem?.let { interactor.deleteSession(it.second.session) }
                recentlyRemovedItem = Pair(position, allHistoryItems[position] as SessionItem)
                allHistoryItems.removeAt(position)
                viewState.deleteSession(position)
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

    fun deleteItemFromDatabase() {
        try {
            recentlyRemovedItem?.let {
                val date = it.second.session.date
                var count = 0
                allHistoryItems.forEach { baseItem ->
                    if (baseItem is SessionItem && baseItem.session.date == date) {
                        count++
                    }
                }
                if (count == 0) {
                    deleteHeaderItem(date)
                }
                interactor.deleteSession(it.second.session)
                recentlyRemovedItem = null
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

    fun restoreItem() {
        try {
            recentlyRemovedItem?.let {
                allHistoryItems.add(it.first, it.second)
                viewState.restoreSession(it.first)
                recentlyRemovedItem = null
            }
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_load_error,
                    e.toString()
                )
            )
        }
    }

    private fun deleteHeaderItem(date: String) {
        try {
            var positionHeader = -1
            allHistoryItems.forEachIndexed { index, element ->
                if (element is DateItem && element.date == date) {
                    positionHeader = index
                }
            }
            if (positionHeader != -1) {
                allHistoryItems.removeAt(positionHeader)
                viewState.deleteHeader(positionHeader)
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