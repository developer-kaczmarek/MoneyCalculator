package kaczmarek.moneycalculator.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.utils.getString

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RVAdapterHistoryBanknote(private val banknotes: List<Banknote>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return R.layout.rv_banknote_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BanknoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BanknoteViewHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int = banknotes.size

    inner class BanknoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvBanknote = itemView.findViewById<TextView>(R.id.tv_banknote)

        fun bind(position: Int) {
            val item = banknotes[position]
            tvBanknote.text = getString(
                R.string.fragment_history_computation,
                item.name,
                item.count,
                item.amount
            )
        }
    }
}