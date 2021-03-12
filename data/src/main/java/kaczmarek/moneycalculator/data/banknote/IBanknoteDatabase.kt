package kaczmarek.moneycalculator.data.banknote

import kaczmarek.moneycalculator.data.base.port.ICrudRepository
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

interface IBanknoteDatabase : ICrudRepository<BanknoteEntity> {
    suspend fun getBanknotes(): List<BanknoteEntity>
    suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean)
}