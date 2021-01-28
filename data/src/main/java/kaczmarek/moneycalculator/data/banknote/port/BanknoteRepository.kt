package kaczmarek.moneycalculator.data.banknote.port

import kaczmarek.moneycalculator.data.banknote.mapper.BanknoteEntityMapper
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class BanknoteRepository(
    private val database: IBanknoteDatabase,
    private val mapper: BanknoteEntityMapper
) : IBanknoteRepository {
    override suspend fun getBanknotes(): List<BanknoteEntity> =
        database.getBanknotes().map { mapper.mapToEntity(it) }.toList()

    override suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean) {
        database.updateVisibilityBanknote(idBanknote, isVisible)
    }

}