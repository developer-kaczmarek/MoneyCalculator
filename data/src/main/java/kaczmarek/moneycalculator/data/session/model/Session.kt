package kaczmarek.moneycalculator.data.session.model

import kaczmarek.moneycalculator.data.banknote.model.Banknote

class Session(
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<Banknote>,
    val dbId: Int?,
)