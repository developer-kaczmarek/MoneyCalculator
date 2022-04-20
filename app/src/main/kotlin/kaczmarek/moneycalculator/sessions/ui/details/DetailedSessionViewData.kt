package kaczmarek.moneycalculator.sessions.ui.details

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.core.utils.getFormattedDateHeader
import kaczmarek.moneycalculator.core.utils.toFormattedAmount
import kaczmarek.moneycalculator.sessions.domain.Session
import me.aartikov.sesame.localizedstring.LocalizedString
import me.aartikov.sesame.localizedstring.plus

data class DetailedSessionViewData(
    val totalAmount: LocalizedString,
    val totalCount: LocalizedString,
    val date: LocalizedString,
    val banknotes: List<DetailedSessionBanknoteViewData>
) {

    companion object {
        val MOCK = DetailedSessionViewData(
            totalAmount = LocalizedString.raw("0"),
            totalCount = LocalizedString.raw("0"),
            date = LocalizedString.raw("18/02/2021"),
            banknotes = emptyList()
        )
    }
}

data class DetailedSessionBanknoteViewData(
    val name: LocalizedString,
    val count: LocalizedString
)

fun Session.toDetailedSessionViewData() = DetailedSessionViewData(
    totalAmount = LocalizedString.resource(
        R.string.session_total_amount,
        totalAmount.toFormattedAmount()
    ),
    totalCount = LocalizedString.resource(R.string.session_total_count, totalCount),
    date = date.getFormattedDateHeader().plus(LocalizedString.raw(" • $time")),
    banknotes = banknotes.map { it.toDetailedSessionBanknoteViewData() }
)

fun DetailedBanknote.toDetailedSessionBanknoteViewData() = DetailedSessionBanknoteViewData(
    name = if (denomination < 1) {
        LocalizedString.resource(R.string.common_name_penny, name)
    } else {
        LocalizedString.resource(R.string.common_name_rub, name)
    },
    count = LocalizedString.resource(R.string.session_banknotes_count, count)
)