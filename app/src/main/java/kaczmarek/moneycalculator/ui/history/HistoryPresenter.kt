package kaczmarek.moneycalculator.ui.history

import android.util.Log
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionUseCase
import kaczmarek.moneycalculator.domain.session.usecase.GetSessionUseCase
import kaczmarek.moneycalculator.ui.base.ItemBase
import kaczmarek.moneycalculator.ui.base.ItemPlaceholder
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */

class HistoryPresenter : PresenterBase<HistoryView>() {
    val items = arrayListOf<ItemBase>()
    private var recentlyRemovedItem: Pair<Int, SessionItem>? = null

    @Inject
    lateinit var getSessionUseCase: GetSessionUseCase

    @Inject
    lateinit var deleteSessionUseCase: DeleteSessionUseCase

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
                val groupSessions = getSessionUseCase.getList().reversed().groupBy { it.date }
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
                Log.e(TAG, e.message.toString())
                viewState.showMessage(
                    getString(
                        R.string.common_load_error,
                        e.toString()
                    )
                )
            }
        }
    }

    fun deleteItemFromList(position: Int) {
        try {
            if (items.isNotEmpty()) {
                // recentlyRemovedItem?.let { interactor.deleteSession(it.second.session) }
                recentlyRemovedItem = Pair(position, items[position] as SessionItem)
                items.removeAt(position)
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
        presenterScope.launch {
            try {
                recentlyRemovedItem?.let {
                    deleteSessionUseCase.delete(it.second.session)
                    recentlyRemovedItem = null
                    getSessions()
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

    fun restoreItem() {
        try {
            recentlyRemovedItem?.let {
                items.add(it.first, it.second)
                viewState.restoreSession(it.first)
                recentlyRemovedItem = null
                getSessions()
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

    companion object {
        const val TAG = "HistoryPresenter"
    }

}