package kaczmarek.moneycalculator.ui.settings

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseItem

const val TYPE_SETTING_BANKNOTE_ITEM = R.layout.rv_setting_banknote_item

data class SettingBanknoteItem(
    val id: Int,
    val name: Float,
    val isShow: Boolean,
    override val itemViewType: Int = TYPE_SETTING_BANKNOTE_ITEM
) : BaseItem {
    override fun getItemId() = id.hashCode()
}