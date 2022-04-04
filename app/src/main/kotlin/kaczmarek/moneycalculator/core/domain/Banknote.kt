package kaczmarek.moneycalculator.core.domain

data class Banknote(
    val id: BanknoteId,
    val name: String,
    val denomination: Double,
    val isShow: Boolean
)

data class DetailedBanknote(
    val id: BanknoteId,
    val name: String,
    val backgroundColor: Long,
    val denomination: Double,
    val isShow: Boolean,
    val count: String,
    val amount: String
)

@JvmInline
value class BanknoteId(val value: String)