package kaczmarek.moneycalculator.ui.history

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.getViewType
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.visible
import kotlin.math.floor
import kotlinx.android.synthetic.main.rv_date_item.view.*
import kotlinx.android.synthetic.main.rv_session_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RVAdapterHistory(private val presenter: PresenterHistory) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var root: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        root = parent

        return when (viewType) {
            R.layout.rv_date_item -> DateItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    viewType,
                    parent,
                    false
                )
            )
            else -> SessionItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    viewType,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateItemViewHolder -> holder.bind(position)
            is SessionItemViewHolder -> holder.bind(position)
        }
    }

    override fun getItemViewType(position: Int) = presenter.allHistoryItems[position].getViewType()

    override fun getItemCount(): Int = presenter.allHistoryItems.size

    inner class SessionItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            itemView.ll_session_header.setOnClickListener(this)
        }

        fun bind(position: Int) {
            val item = presenter.allHistoryItems[position] as SessionItem
            itemView.tv_session_time.text = item.session.time
            if (item.session.totalAmount - floor(item.session.totalAmount) == 0F) {
                itemView.tv_session_total_amount.text =
                    getString(R.string.common_ruble_format, floor(item.session.totalAmount).toInt())
            } else {
                itemView.tv_session_total_amount.text =
                    getString(R.string.common_ruble_float_format, item.session.totalAmount)
            }
            itemView.rv_session_banknotes.adapter =
                RVAdapterHistoryBanknote(
                    item.session.banknotes
                )
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ll_session_header -> {
                    if (itemView.rv_session_banknotes.visibility == View.GONE) {
                        itemView.iv_session_more.animate().rotation(180F).start()
                        TransitionManager.beginDelayedTransition(root, AutoTransition())
                        itemView.rv_session_banknotes.visible
                    } else {
                        itemView.iv_session_more.animate().rotation(0F).start()
                        TransitionManager.beginDelayedTransition(root)
                        itemView.rv_session_banknotes.gone
                    }
                }
            }
        }
    }

    inner class DateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            val item = presenter.allHistoryItems[position] as DateItem
            val calendar = Calendar.getInstance()
            val currentDate = calendar.time
            calendar.add(Calendar.DATE, -1)
            val yesterdayDate = calendar.time
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            when (item.date) {
                formatter.format(currentDate) -> itemView.tv_date_title.setText(R.string.fragment_history_today_sessions)
                formatter.format(yesterdayDate) -> itemView.tv_date_title.setText(R.string.fragment_history_yesterday_sessions)
                else -> itemView.tv_date_title.text = item.date
            }
        }
    }

}