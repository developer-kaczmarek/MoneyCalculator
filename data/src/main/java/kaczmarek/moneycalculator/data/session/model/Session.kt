package kaczmarek.moneycalculator.data.session.model

import kaczmarek.moneycalculator.data.banknote.model.Banknote

data class Session(
    val id: Int,
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<Banknote>
)