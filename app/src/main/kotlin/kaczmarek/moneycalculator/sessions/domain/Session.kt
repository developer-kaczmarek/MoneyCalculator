package kaczmarek.moneycalculator.sessions.domain

import android.os.Parcelable
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session(
    val id: SessionId,
    val date: String,
    val time: String,
    val totalAmount: Double,
    val totalCount: Int,
    val banknotes: List<DetailedBanknote>
) : Parcelable

@JvmInline
@Parcelize
value class SessionId(val value: String) : Parcelable