package kaczmarek.moneycalculator.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseListAdapter
import kaczmarek.moneycalculator.ui.base.BaseViewHolder
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.isInteger

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

        private val tvStart = view.findViewById<TextView>(R.id.tv_start_column)
        private val tvCenter = view.findViewById<TextView>(R.id.tv_center_column)
        private val tvEnd = view.findViewById<TextView>(R.id.tv_end_column)

        override fun bind() {
            val item = getItem(adapterPosition) as SessionDetailsItem
            tvStart.text = if (item.name.toDouble().isInteger()) {
                getString(R.string.common_ruble_format, item.name.toInt())
            } else {
                getString(R.string.common_ruble_float_format, item.name)
            }
            tvCenter.text = "${item.count} шт."
            tvEnd.text = getString(R.string.common_ruble_float_format, item.amount)
        }
    }
}