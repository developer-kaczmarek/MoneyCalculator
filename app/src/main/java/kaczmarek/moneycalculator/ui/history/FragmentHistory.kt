package kaczmarek.moneycalculator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.FragmentBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.BackStackChangeListenerMain
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.visible
import moxy.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_history.*
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class FragmentHistory : FragmentBase(),
    ViewHistory, ListenerDeleteItemHistory {

    @InjectPresenter
    lateinit var presenter: PresenterHistory
    private val adapter: RVAdapterHistorySession by lazy {
        RVAdapterHistorySession(
            presenter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
            (activity as? BackStackChangeListenerMain)?.onBackStackChange(this)
        }

        context?.let {
            ItemTouchHelper(SwipeCallbackHistory(0, ItemTouchHelper.LEFT, this)).apply {
                attachToRecyclerView(rv_history)
            }
        }

        rv_history.adapter = adapter
        presenter.getSessions()
    }

    override fun onStart() {
        super.onStart()
        setTitle(R.string.activity_main_title_history_item)
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    override fun updateSessions() {
        showSessions()
        adapter.notifyDataSetChanged()
    }

    override fun deleteSession(position: Int) {
        adapter.notifyItemRemoved(position)
        Snackbar.make(
            cl_history_container,
            getString(R.string.fragment_history_session_delete),
            Snackbar.LENGTH_LONG
        )
            .apply {
                setActionTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_ffd))
                setAction(R.string.common_cancel) {
                    presenter.restoreItem()
                }
                addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT
                            || event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_MANUAL
                        ) {
                            presenter.deleteItemFromDatabase()
                        }
                    }
                })
                show()
            }
    }

    override fun restoreSession(position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun deleteHeader(positionHeader: Int) {
        if (presenter.allHistoryItems.isNotEmpty()) {
            adapter.notifyItemRemoved(positionHeader)
        } else {
            showSessions()
        }
    }

    private fun showSessions() {
        if (presenter.allHistoryItems.isNotEmpty()) {
            rv_history.visible
            tv_empty_history.gone
        } else {
            rv_history.gone
            tv_empty_history.visible
        }
    }

    override fun onSwipe(position: Int) {
        presenter.deleteItemFromList(position)
    }

    companion object {
        const val TAG = "HistoryFragment"
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewHistory : ViewBase {
    fun showMessage(message: String)
    fun updateSessions()
    fun deleteSession(position: Int)
    fun restoreSession(position: Int)
    fun deleteHeader(positionHeader: Int)

}