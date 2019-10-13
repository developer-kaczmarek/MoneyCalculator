package com.kaczmarek.moneycalculator.ui.history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.adapters.getViewType
import com.kaczmarek.moneycalculator.ui.history.models.DateItem
import com.kaczmarek.moneycalculator.ui.history.models.SessionItem
import com.kaczmarek.moneycalculator.ui.history.presenters.HistoryPresenter
import com.kaczmarek.moneycalculator.utils.getString
import kotlin.math.floor
import com.ub.utils.base.BaseRVAdapter
import com.ub.utils.gone
import com.ub.utils.visible
import kotlinx.android.synthetic.main.rv_date_item.view.*
import kotlinx.android.synthetic.main.rv_session_item.view.*

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class HistoryRVAdapter(private val presenter: HistoryPresenter) : BaseRVAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        R.layout.rv_date_item -> DateItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
        else -> SessionItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateItemViewHolder -> holder.bind(position)
            is SessionItemViewHolder -> holder.bind(position)
        }
    }

    override fun getItemViewType(position: Int) = presenter.allHistoryItems[position].getViewType()

    override fun getItemCount(): Int = presenter.allHistoryItems.size

    inner class SessionItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.iv_session_more.setOnClickListener(this)
        }

        fun bind(position: Int) {
            val item = presenter.allHistoryItems[position] as SessionItem
            itemView.tv_session_time.text = item.session.time
            if (item.session.totalAmount - floor(item.session.totalAmount) == 0F) {
                itemView.tv_session_total_amount.text = getString(R.string.common_ruble_format, floor(item.session.totalAmount).toInt())
            } else {
                itemView.tv_session_total_amount.text = getString(R.string.common_ruble_float_format, item.session.totalAmount)
            }
            itemView.rv_session_banknotes.adapter = BanknoteRVAdapter(item.session.banknotes)
        }

        override fun onClick(v: View) {
            when(v.id){
                R.id.iv_session_more -> {
                    if(itemView.rv_session_banknotes.visibility == View.GONE){
                        itemView.rv_session_banknotes.visible
                    } else {
                        itemView.rv_session_banknotes.gone
                    }
                }
            }
           // listener?.onClick(v, adapterPosition)
        }
    }

    inner class DateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            val item = presenter.allHistoryItems[position] as DateItem
            itemView.tv_date_title.text = item.date
        }
    }

}