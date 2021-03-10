package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionByModelUseCase
import kaczmarek.moneycalculator.domain.session.usecase.GetSessionsListUseCase
import kaczmarek.moneycalculator.ui.base.ItemBase
import kaczmarek.moneycalculator.ui.base.ItemPlaceholder
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.logError
import kotlinx.coroutines.launch
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */

class HistoryPresenter : PresenterBase<HistoryView>() {

    private val items = arrayListOf<ItemBase>()

    private var recentlyRemovedItem: Pair<Int, SessionItem>? = null

    @Inject
    lateinit var getSessionsListUseCase: GetSessionsListUseCase

    @Inject
    lateinit var deleteSessionByModelUseCase: DeleteSessionByModelUseCase

    init {
        DIManager.getHistorySubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeHistorySubcomponent()
    }

    fun getSessions() {
        presenterScope.launch {
            try {
                items.clear()
                val groupSessions = getSessionsListUseCase.invoke().reversed().groupBy { it.date }
                if (groupSessions.isNullOrEmpty()) {
                    items.add(ItemPlaceholder())
                } else {
                    groupSessions.forEach { session ->
                        items.add(DateItem(session.key))
                        session.value.sortedByDescending { it.time }.forEach {
                            items.add(SessionItem(it, false))
                        }
                    }
                }
                viewState.updateSessions(items)
            } catch (e: Exception) {
                logError(TAG, e.message)
                viewState.showMessage(
                    getString(
                        R.string.common_load_error,
                        e.toString()
                    )
                )
            }
        }
    }

    fun deleteSessionItemTemporarily(position: Int) {
        presenterScope.launch {
            try {
                if (items.isNotEmpty()) {
                    recentlyRemovedItem?.let {
                        deleteSessionByModelUseCase.invoke(it.second.session)
                    }
                    recentlyRemovedItem = Pair(position, items[position] as SessionItem)
                    items.removeAt(position)
                    with(viewState) {
                        updateSessions(items)
                        showInfoAboutDeletingSessionItem()
                    }
                }
            } catch (e: Exception) {
                logError(TAG, e.message)
                viewState.showMessage(
                    getString(
                        R.string.common_delete_error,
                        e.toString()
                    )
                )
            }
        }
    }

    fun deleteSessionItemForever() {
        presenterScope.launch {
            try {
                recentlyRemovedItem?.let {
                    deleteSessionByModelUseCase.invoke(it.second.session)
                    getSessions()
                }
                recentlyRemovedItem = null
            } catch (e: Exception) {
                logError(TAG, e.message)
                viewState.showMessage(
                    getString(
                        R.string.common_delete_error,
                        e.toString()
                    )
                )
            }
        }
    }

    fun restoreSessionItem() {
        recentlyRemovedItem = null
        getSessions()
    }

    fun changeDetailsVisibility(position: Int) {
        try {
            val selectedSession = items[position] as SessionItem
            (items[position] as SessionItem).isShowDetails = !selectedSession.isShowDetails
            viewState.updateSessions(items)
        } catch (e: Exception) {
            logError(TAG, e.message)
            viewState.showMessage(
                getString(
                    R.string.common_edit_error,
                    e.toString()
                )
            )
        }
    }

    companion object {
        const val TAG = "HistoryPresenter"
    }
}