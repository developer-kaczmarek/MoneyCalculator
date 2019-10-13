package com.kaczmarek.moneycalculator.ui.history.fragmens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.fragmens.BaseFragment
import com.kaczmarek.moneycalculator.ui.history.presenters.HistoryPresenter
import com.kaczmarek.moneycalculator.ui.history.views.HistoryView
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.kaczmarek.moneycalculator.utils.FragmentNavigation
import moxy.presenter.InjectPresenter
import com.kaczmarek.moneycalculator.ui.history.adapters.HistoryRVAdapter
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class HistoryFragment : BaseFragment(), HistoryView, FragmentNavigation{

    @InjectPresenter
    lateinit var presenter: HistoryPresenter
    private var navigationListener: ExternalNavigation? = null
    private val adapter: HistoryRVAdapter by lazy { HistoryRVAdapter(presenter) }

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
            (activity as? BackStackChangeListener)?.onBackStackChange(this)
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
        adapter.notifyDataSetChanged()
    }


    companion object {
        const val TAG = "HistoryFragment"

        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}