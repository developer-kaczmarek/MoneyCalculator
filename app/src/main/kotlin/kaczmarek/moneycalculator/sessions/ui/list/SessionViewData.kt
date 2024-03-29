package kaczmarek.moneycalculator.sessions.ui.list

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.utils.getFormattedDateHeader
import kaczmarek.moneycalculator.core.utils.toFormattedAmount
import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId
import me.aartikov.sesame.localizedstring.LocalizedString
import java.util.*

sealed class SessionViewData(open val id: String) {

    data class DateViewData(val value: LocalizedString) : SessionViewData(id = value.toString())

    data class DetailsViewData(
        val sessionId: SessionId,
        val totalAmount: LocalizedString,
        val totalCount: LocalizedString,
        val time: String,
        val output: Session
    ) : SessionViewData(sessionId.value)

    companion object {
        val MOCK = DetailsViewData(
            sessionId = SessionId("1"),
            totalAmount = LocalizedString.raw("0"),
            totalCount = LocalizedString.raw("0"),
            time = "18/02/2021",
            output = Session(
                id = SessionId("1"),
                date = "18/02/2021",
                time = "17:22",
                totalAmount = 0.0,
                totalCount = 0,
                banknotes = emptyList()
            )
        )

        fun mocks(count: Int = 7): List<SessionViewData> {
            return List(count) { MOCK.copy(sessionId = SessionId(UUID.randomUUID().toString())) }
        }
    }
}

fun String.toViewData() = SessionViewData.DateViewData(
    value = getFormattedDateHeader()
)

fun Session.toViewData() = SessionViewData.DetailsViewData(
    sessionId = id,
    totalAmount = LocalizedString.resource(
        R.string.common_name_rub,
        totalAmount.toFormattedAmount()
    ),
    totalCount = LocalizedString.resource(R.string.common_total_count, totalCount),
    time = time,
    output = this
)