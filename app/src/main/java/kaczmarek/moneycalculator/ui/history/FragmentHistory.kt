package kaczmarek.moneycalculator.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.FragmentBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.BackStackChangeListenerMain
import kaczmarek.moneycalculator.utils.ExternalNavigation
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
    ViewHistory {

    @InjectPresenter
    lateinit var presenter: PresenterHistory
    private var navigationListener: ExternalNavigation? = null
    private val adapter: RVAdapterHistory by lazy {
        RVAdapterHistory(
            presenter
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as? ExternalNavigation
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

    private fun showSessions() {
        if (presenter.allHistoryItems.size != 0) {
            rv_history.visible
            tv_empty_history.gone
        } else {
            rv_history.gone
            tv_empty_history.visible
        }
    }


    companion object {
        const val TAG = "HistoryFragment"
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewHistory : ViewBase {
    fun showMessage(message: String)
    fun updateSessions()

}