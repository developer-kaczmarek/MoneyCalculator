package kaczmarek.moneycalculator.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseListAdapter
import kaczmarek.moneycalculator.ui.base.BaseViewHolder
import kaczmarek.moneycalculator.utils.getString

class HistorySessionDetailsRVAdapter : BaseListAdapter<SessionDetailsItem,
        HistorySessionDetailsRVAdapter.SessionDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return SessionDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionDetailsViewHolder, position: Int) {
        holder.bind()
    }

    inner class SessionDetailsViewHolder(view: View) : BaseViewHolder(view) {

        private val tvSessionDetailsBanknote = view.findViewById<TextView>(R.id.tv_banknote)

        override fun bind() {
            val item = getItem(adapterPosition) as SessionDetailsItem
            tvSessionDetailsBanknote.text = getString(
                R.string.fragment_history_computation,
                item.name,
                item.count,
                item.amount
            )
        }
    }
}