package kaczmarek.moneycalculator.data.banknote.model

data class Banknote(
    val id: Int,
    val name: Float,
    val count: Int,
    val amount: Float,
    val backgroundColor: String,
    val textColor: String,
    val isShow: Boolean
)