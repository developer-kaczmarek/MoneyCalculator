package kaczmarek.moneycalculator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.FragmentHistoryBinding
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ItemBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import moxy.ktx.moxyPresenter


/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */

interface HistoryView : ViewBase {
    fun updateSessions(list: List<ItemBase>)
    fun deleteSession(position: Int)
    fun restoreSession(position: Int)
    fun deleteHeader(positionHeader: Int)
}

class HistoryFragment : BaseFragment(R.layout.fragment_history), HistoryView,
    ListenerDeleteItemHistory {

    private val presenter by moxyPresenter { HistoryPresenter() }
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapter: RVAdapterHistorySession by lazy {
        RVAdapterHistorySession(
            presenter
        )
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

        context?.let {
            ItemTouchHelper(SwipeCallbackHistory(0, ItemTouchHelper.LEFT, this)).apply {
                attachToRecyclerView(binding.rvHistory)
            }
        }

        binding.rvHistory.adapter = adapter
        presenter.getSessions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun updateSessions(list: List<ItemBase>) {
        adapter.update(list)
    }

    override fun deleteSession(position: Int) {
        adapter.notifyItemRemoved(position)
        Snackbar.make(
            binding.clHistoryContainer,
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
        adapter.notifyItemRemoved(positionHeader)
    }

    override fun onSwipe(position: Int) {
        presenter.deleteItemFromList(position)
    }

    companion object {
        const val TAG = "HistoryFragment"
    }
}