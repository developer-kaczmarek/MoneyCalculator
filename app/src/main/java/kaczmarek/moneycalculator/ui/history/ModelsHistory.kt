package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.services.database.models.Session
import kaczmarek.moneycalculator.ui.base.ItemBase

const val TYPE_DATE_ITEM = R.layout.rv_date_item
const val TYPE_SESSION_ITEM = R.layout.rv_session_item

class DateItem(val date: String, override val itemViewType: Int = TYPE_DATE_ITEM) : ItemBase {
    override fun getItemId() = hashCode()
}

class SessionItem(
    val session: Session,
    var isShow: Boolean,
    override val itemViewType: Int = TYPE_SESSION_ITEM
) : ItemBase {
    override fun getItemId() = hashCode()
}