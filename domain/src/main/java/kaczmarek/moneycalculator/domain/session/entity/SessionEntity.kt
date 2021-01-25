package kaczmarek.moneycalculator.domain.session.entity

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

data class SessionEntity(
    val id: Int,
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<BanknoteEntity>
)

