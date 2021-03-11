package kaczmarek.moneycalculator.domain.banknote.port

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

interface IBanknoteRepository {

    suspend fun getBanknotes(): List<BanknoteEntity>

    suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean)

    fun saveBanknotesTemporary(banknotes: List<BanknoteEntity>)

    fun getBanknotesFromTemporaryStorage(): List<BanknoteEntity>

}