package kaczmarek.moneycalculator.data.banknote

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class BanknoteRepository(
    private val database: IBanknoteDatabase,
    private val temporaryStorageService: ITemporaryStorageService,
) : IBanknoteRepository {
    override suspend fun getBanknotes(): List<BanknoteEntity> = database.getBanknotes()

    override suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean) {
        database.updateVisibilityBanknote(idBanknote, isVisible)
    }

    override fun saveBanknotesTemporary(banknotes: List<BanknoteEntity>) {
        temporaryStorageService.saveBanknotesFromCurrentSession(banknotes)
    }

    override fun getBanknotesFromTemporaryStorage(): List<BanknoteEntity> =
        temporaryStorageService.getBanknotesIntoCurrentSession()

}