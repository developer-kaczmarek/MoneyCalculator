package kaczmarek.moneycalculator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.FragmentHistoryBinding
import kaczmarek.moneycalculator.ui.base.BaseClickListener
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ItemBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */

interface HistoryView : ViewBase {
    fun updateSessions(sessions: List<ItemBase>)
    fun showInfoAboutDeletingSessionItem()
}

class HistoryFragment : BaseFragment(R.layout.fragment_history), HistoryView, BaseClickListener,
    HistoryDeleteItemListener {

    private val presenter by moxyPresenter { HistoryPresenter() }

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    private var rvAdapter: HistorySessionsRVAdapter? = null

    private var deletingItemSnackBar: Snackbar? = null

    private val cancelDeletingItemCallback = object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_MANUAL) {
                presenter.deleteSessionItemForever()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ItemTouchHelper(HistorySwipeCallback(0, ItemTouchHelper.LEFT, this)).apply {
            attachToRecyclerView(binding.rvHistory)
        }
        rvAdapter = HistorySessionsRVAdapter().apply {
            clicklistener = this@HistoryFragment
        }
        binding.rvHistory.adapter = rvAdapter
        presenter.getSessions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvAdapter?.clicklistener = null
        rvAdapter = null
        deletingItemSnackBar?.removeCallback(cancelDeletingItemCallback)
        _binding = null
    }

    override fun updateSessions(sessions: List<ItemBase>) {
        rvAdapter?.update(sessions)
    }

    override fun showInfoAboutDeletingSessionItem() {
        deletingItemSnackBar = Snackbar.make(
            binding.clHistoryContainer,
            getString(R.string.fragment_history_session_delete),
            Snackbar.LENGTH_LONG
        ).apply {
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_ffd))
            setAction(R.string.common_cancel) {
                presenter.restoreSessionItem()
            }
            addCallback(cancelDeletingItemCallback)
            show()
        }
    }

    override fun onClick(view: View, position: Int) {
        presenter.changeDetailsVisibility(position)
    }

    override fun onSwipe(position: Int) {
        presenter.deleteSessionItemTemporarily(position)
    }

    companion object {
        const val TAG = "HistoryFragment"
    }
}