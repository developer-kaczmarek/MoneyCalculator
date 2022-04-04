package kaczmarek.moneycalculator.core.data.storage.banknotes

import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.domain.DetailedBanknote

class BanknotesStorageImpl(private val banknotesDao: BanknotesDao) : BanknotesStorage {

    override suspend fun getBanknotes(): List<Banknote> {
        return banknotesDao.getBanknotes().map { it.toBanknote() }
    }

    override suspend fun getVisibleBanknotes(): List<DetailedBanknote> {
        return banknotesDao.getBanknotesByVisibility().map { it.toDetailedBanknote() }
    }

    override suspend fun updateBanknoteVisibility(banknote: Banknote) {
        banknotesDao.updateBanknoteVisibility(banknote.isShow, banknote.id.value.toInt())
    }
}