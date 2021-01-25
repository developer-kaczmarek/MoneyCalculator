package kaczmarek.moneycalculator.domain.banknote.port

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

interface IBanknoteRepository {

    suspend fun getBanknotes(): List<BanknoteEntity>

}