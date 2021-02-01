package kaczmarek.moneycalculator.domain.banknote.entity

data class BanknoteEntity(
    val id: Int,
    val name: Float,
    val count: Int,
    val amount: Float,
    val backgroundColor: String,
    val isShow: Boolean
)