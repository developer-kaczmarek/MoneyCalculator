package kaczmarek.moneycalculator.sessions.domain

import kaczmarek.moneycalculator.core.domain.DetailedBanknote

data class Session(
    val id: SessionId,
    val date: String,
    val time: String,
    val totalAmount: Double,
    val totalCount: Int,
    val banknotes: List<DetailedBanknote>
)

@JvmInline
value class SessionId(val value: String)