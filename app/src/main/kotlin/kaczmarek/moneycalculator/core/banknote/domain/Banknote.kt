package kaczmarek.moneycalculator.core.banknote.domain

import kotlinx.serialization.Serializable

data class Banknote(
    val id: BanknoteId,
    val name: String,
    val denomination: Double,
    val isShow: Boolean
)

@Serializable
data class DetailedBanknote(
    val id: BanknoteId,
    val name: String,
    val backgroundColor: Long,
    val denomination: Double,
    val isShow: Boolean,
    val count: String,
    val amount: String
)

@Serializable
@JvmInline
value class BanknoteId(val value: String)