package kaczmarek.moneycalculator.ui.history

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseListAdapter
import kaczmarek.moneycalculator.ui.base.BaseViewHolder
import kaczmarek.moneycalculator.ui.base.ItemBase
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.visible
import kotlin.math.floor
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RVAdapterHistorySession(private val presenter: HistoryPresenter) :
    BaseListAdapter<ItemBase, BaseViewHolder>() {

    lateinit var root: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        root = parent
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            TYPE_DATE_ITEM -> DateItemViewHolder(view)
            else -> SessionItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind()
    }

    inner class SessionItemViewHolder(view: View) : BaseViewHolder(view),
        View.OnClickListener {
        private val tvTime = itemView.findViewById<TextView>(R.id.tv_session_time)
        private val tvTotalAmount = itemView.findViewById<TextView>(R.id.tv_session_total_amount)
        private val rvBanknotes = itemView.findViewById<RecyclerView>(R.id.rv_session_banknotes)
        private val ivMoreDetails = itemView.findViewById<ImageView>(R.id.iv_session_more)
        private val llHeader = itemView.findViewById<LinearLayout>(R.id.ll_session_header)

        init {
            llHeader.setOnClickListener(this)
        }

        override fun bind() {
            super.bind()
            val item = getItem(adapterPosition) as SessionItem
            tvTime.text = item.session.time
            tvTotalAmount.text =
                if (item.session.totalAmount - floor(item.session.totalAmount) == 0.0) {
                    getString(R.string.common_ruble_format, floor(item.session.totalAmount).toInt())
                } else {
                    getString(R.string.common_ruble_float_format, item.session.totalAmount)
                }
            rvBanknotes.adapter = RVAdapterHistoryBanknote(item.session.banknotes)

            if (item.isShow) {
                ivMoreDetails.rotation = 180F
                rvBanknotes.visible
            } else {
                ivMoreDetails.rotation = 0F
                rvBanknotes.gone
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            when (v.id) {
                R.id.ll_session_header -> {
                    val item = presenter.allHistoryItems[position] as SessionItem
                    if (!item.isShow) {
                        ivMoreDetails.animate().rotation(180F).start()
                        TransitionManager.beginDelayedTransition(root, AutoTransition())
                        rvBanknotes.visible
                    } else {
                        ivMoreDetails.animate().rotation(0F).start()
                        TransitionManager.beginDelayedTransition(root)
                        rvBanknotes.gone
                    }
                    item.isShow = !item.isShow
                }
            }
        }
    }

    inner class DateItemViewHolder(view: View) : BaseViewHolder(view) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_date_title)

        override fun bind() {
            super.bind()
            val item = getItem(adapterPosition) as DateItem
            val calendar = Calendar.getInstance()
            val currentDate = calendar.time
            calendar.add(Calendar.DATE, -1)
            val yesterdayDate = calendar.time
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            tvTitle.text = when (item.date) {
                formatter.format(currentDate) -> getString(R.string.fragment_history_today_sessions)
                formatter.format(yesterdayDate) -> getString(R.string.fragment_history_yesterday_sessions)
                else -> item.date
            }
        }
    }
}