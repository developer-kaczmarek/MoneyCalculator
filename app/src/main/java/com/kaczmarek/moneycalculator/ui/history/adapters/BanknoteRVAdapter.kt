package com.kaczmarek.moneycalculator.ui.history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.utils.getString
import com.ub.utils.base.BaseRVAdapter
import kotlinx.android.synthetic.main.rv_banknote_item.view.*

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class BanknoteRVAdapter(private val banknotes: List<Banknote>) : BaseRVAdapter<RecyclerView.ViewHolder>() {

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

        fun bind(position: Int) {
            val item = banknotes[position]
            itemView.tv_banknote_name.text = getString(R.string.common_ruble_float_format, item.name)
            itemView.tv_banknote_count.text = item.count.toString()
            itemView.tv_banknote_amount.text = getString(R.string.common_ruble_float_format, item.amount)
        }
    }
}