package com.kaczmarek.moneycalculator.ui.base.adapters

import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.history.models.DateItem
import com.kaczmarek.moneycalculator.ui.history.models.SessionItem

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
interface BaseItem
fun BaseItem.getViewType() = when (this) {
    is DateItem -> R.layout.rv_date_item
    is SessionItem -> R.layout.rv_session_item
    else -> -1
}