package kaczmarek.moneycalculator.data.banknote.port

import kaczmarek.moneycalculator.data.banknote.mapper.BanknoteEntityMapper
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository
import sun.rmi.runtime.Log

class BanknoteRepository(
    private val database: IBanknoteDatabase,
    private val temporaryStorageService: ITemporaryStorageService,
    private val mapper: BanknoteEntityMapper
) : IBanknoteRepository {
    override suspend fun getBanknotes(): List<BanknoteEntity> =
        database.getBanknotes().map { mapper.mapToEntity(it) }.toList()

    override suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean) {
        database.updateVisibilityBanknote(idBanknote, isVisible)
    }

    override fun saveBanknotesTemporary(banknotes: List<BanknoteEntity>) {
        temporaryStorageService.saveBanknotesFromCurrentSession(banknotes)
    }

    override fun getBanknotesFromTemporaryStorage(): List<BanknoteEntity> =
        temporaryStorageService.getBanknotesIntoCurrentSession()

}