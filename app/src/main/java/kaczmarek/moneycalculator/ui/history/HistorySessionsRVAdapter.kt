package kaczmarek.moneycalculator.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.*
import kaczmarek.moneycalculator.utils.getFormattedAmount
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.visible
import java.text.SimpleDateFormat
import java.util.*

class HistorySessionsRVAdapter : BaseListAdapter<BaseItem, BaseViewHolder>() {

    var root: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        root = parent
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            TYPE_HISTORY_DATE_ITEM -> DateItemViewHolder(view)
            TYPE_HISTORY_SESSION_ITEM -> SessionItemViewHolder(view)
            else -> ItemPlaceholderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind()
    }

    inner class SessionItemViewHolder(view: View) : BaseViewHolder(view), View.OnClickListener {

        private val tvSaveTime = view.findViewById<TextView>(R.id.tv_session_time)
        private val tvTotalAmount = view.findViewById<TextView>(R.id.tv_session_total_amount)

        private val rvSessionDetails = view.findViewById<RecyclerView>(R.id.rv_session_details)

        private val ivMoreDetails = view.findViewById<ImageView>(R.id.iv_session_more)

        private val llHeader = view.findViewById<LinearLayout>(R.id.ll_session_header)

        private val sessionDetails = arrayListOf<SessionDetailsItem>()

        init {
            llHeader.setOnClickListener(this)
        }

        override fun bind() {
            super.bind()
            val item = getItem(adapterPosition) as SessionItem
            tvSaveTime.text = item.session.time
            tvTotalAmount.text = item.session.totalAmount.getFormattedAmount()+" • 500 шт."
            val sessionDetailsRVAdapter = HistorySessionDetailsRVAdapter()
            rvSessionDetails.adapter = sessionDetailsRVAdapter
            sessionDetails.apply {
                clear()
                item.session.banknotes.map {
                    add(SessionDetailsItem(it.name, it.count, it.amount))
                }
                sessionDetailsRVAdapter.update(sessionDetails)
            }

            if (item.isShowDetails) {
                ivMoreDetails.rotation = 180F
                rvSessionDetails.visible
            } else {
                ivMoreDetails.rotation = 0F
                rvSessionDetails.gone
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            root?.let {
                val item = getItem(adapterPosition) as SessionItem
                if (!item.isShowDetails) {
                    ivMoreDetails.animate().rotation(180F).start()
                    TransitionManager.beginDelayedTransition(it, AutoTransition())
                    rvSessionDetails.visible
                } else {
                    ivMoreDetails.animate().rotation(0F).start()
                    TransitionManager.beginDelayedTransition(it)
                    rvSessionDetails.gone

                }
            }
            clicklistener?.onClick(v, position)
        }
    }

    inner class DateItemViewHolder(view: View) : BaseViewHolder(view) {

        private val tvTitle = view.findViewById<TextView>(R.id.tv_date_title)

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