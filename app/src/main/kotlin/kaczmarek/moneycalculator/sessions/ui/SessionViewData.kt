package kaczmarek.moneycalculator.sessions.ui

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.ui.utils.getFormattedDateHeader
import kaczmarek.moneycalculator.core.ui.utils.toFormattedAmount
import kaczmarek.moneycalculator.sessions.domain.Session
import me.aartikov.sesame.localizedstring.LocalizedString

sealed class SessionViewData {
    data class DateViewData(val value: LocalizedString) : SessionViewData()

    data class DetailsViewData(
        val totalAmount: LocalizedString,
        val totalCount: LocalizedString,
        val time: String
    ) : SessionViewData()
}

fun String.toViewData() = SessionViewData.DateViewData(
    value = getFormattedDateHeader()
)

fun Session.toViewData() = SessionViewData.DetailsViewData(
    totalAmount = LocalizedString.resource(
        R.string.common_name_rub,
        totalAmount.toFormattedAmount()
    ),
    totalCount = LocalizedString.resource(R.string.history_total_count, totalCount),
    time = time
)