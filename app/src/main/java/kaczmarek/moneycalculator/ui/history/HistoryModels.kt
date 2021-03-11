package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.ui.base.BaseItem

const val TYPE_HISTORY_DATE_ITEM = R.layout.rv_history_date_save_session_item
const val TYPE_HISTORY_SESSION_ITEM = R.layout.rv_history_session_item
const val TYPE_HISTORY_SESSION_DETAILS_ITEM = R.layout.rv_history_session_details_item

class DateItem(
    val date: String,
    override val itemViewType: Int = TYPE_HISTORY_DATE_ITEM
) : BaseItem {
    override fun getItemId() = date.hashCode()
}

class SessionItem(
    val session: SessionEntity,
    var isShowDetails: Boolean,
    override val itemViewType: Int = TYPE_HISTORY_SESSION_ITEM
) : BaseItem {
    override fun getItemId() = session.dbId.hashCode()
}

class SessionDetailsItem(
    val name: Float,
    val count: Int,
    val amount: Float,
    override val itemViewType: Int = TYPE_HISTORY_SESSION_DETAILS_ITEM
) : BaseItem {
    override fun getItemId() = hashCode()
}