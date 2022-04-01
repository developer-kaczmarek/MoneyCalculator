package kaczmarek.moneycalculator.core.domain

data class Banknote(
    val id: BanknoteId,
    val name: String,
    val backgroundColor: String,
    val value: Float,
    val isShow: Boolean
)

@JvmInline
value class BanknoteId(val value: String)