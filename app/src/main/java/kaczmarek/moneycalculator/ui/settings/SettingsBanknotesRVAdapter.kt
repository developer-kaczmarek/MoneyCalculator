package kaczmarek.moneycalculator.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseListAdapter
import kaczmarek.moneycalculator.ui.base.BaseViewHolder
import kaczmarek.moneycalculator.utils.getString

class SettingsBanknotesRVAdapter :
    BaseListAdapter<SettingBanknoteItem, SettingsBanknotesRVAdapter.SettingBanknoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingBanknoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return SettingBanknoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingBanknoteViewHolder, position: Int) {
        holder.bind()
    }

    inner class SettingBanknoteViewHolder(view: View) : BaseViewHolder(view),
        CompoundButton.OnCheckedChangeListener {

        private val checkBoxVisibility =
            view.findViewById<AppCompatCheckBox>(R.id.cb_banknote_visibility)

        init {
            checkBoxVisibility.setOnCheckedChangeListener(this)
        }

        override fun bind() {
            super.bind()
            val item = getItem(adapterPosition)
            with(checkBoxVisibility) {
                isChecked = item.isShow
                text = if (item.name >= 1) getString(
                    R.string.common_ruble_format,
                    item.name.toInt()
                ) else getString(R.string.common_penny_format, (item.name * 100).toInt())
            }
        }

        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            checkChangeListener?.onChange(buttonView, position, isChecked)
        }
    }
}