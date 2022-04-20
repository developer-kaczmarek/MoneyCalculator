package kaczmarek.moneycalculator.core.banknote.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class Banknote(
    val id: BanknoteId,
    val name: String,
    val denomination: Double,
    val isShow: Boolean
)

@Serializable
@Parcelize
data class DetailedBanknote(
    val id: BanknoteId,
    val name: String,
    val backgroundColor: Long,
    val denomination: Double,
    val isShow: Boolean,
    val count: String,
    val amount: String
) : Parcelable

@Serializable
@JvmInline
@Parcelize
value class BanknoteId(val value: String) : Parcelable