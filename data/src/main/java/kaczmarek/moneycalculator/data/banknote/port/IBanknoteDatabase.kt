package kaczmarek.moneycalculator.data.banknote.port

import kaczmarek.moneycalculator.data.banknote.model.Banknote
import kaczmarek.moneycalculator.data.base.port.ICrudRepository

interface IBanknoteDatabase : ICrudRepository<Banknote> {

    suspend fun getBanknotes(): List<Banknote>

    suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean)

}