package kaczmarek.moneycalculator.ui.base

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.history.DateItem
import kaczmarek.moneycalculator.ui.history.SessionItem

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
interface BaseItem
fun BaseItem.getViewType() = when (this) {
    is DateItem -> R.layout.rv_date_item
    is SessionItem -> R.layout.rv_session_item
    else -> -1
}