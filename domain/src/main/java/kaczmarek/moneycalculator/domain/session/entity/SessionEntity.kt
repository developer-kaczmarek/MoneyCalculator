package kaczmarek.moneycalculator.domain.session.entity

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

class SessionEntity(
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<BanknoteEntity>,
    val dbId: Int?,
)

