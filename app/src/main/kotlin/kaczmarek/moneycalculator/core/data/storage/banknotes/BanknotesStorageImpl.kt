package kaczmarek.moneycalculator.core.data.storage.banknotes

import kaczmarek.moneycalculator.core.domain.Banknote

class BanknotesStorageImpl(private val banknotesDao: BanknotesDao) : BanknotesStorage {

    override suspend fun getBanknotes(): List<Banknote> {
        return banknotesDao.getBanknotes().map { it.toDomain() }
    }

    override suspend fun updateBanknoteVisibility(banknote: Banknote) {
        banknotesDao.updateBanknoteVisibility(banknote.isShow, banknote.id.value.toInt())
    }
}