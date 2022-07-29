package kaczmarek.moneycalculator.core.banknote.data

import kaczmarek.moneycalculator.core.banknote.domain.Banknote
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kotlinx.coroutines.flow.MutableStateFlow

class BanknotesStorageImpl(private val banknotesDao: BanknotesDao) : BanknotesStorage {

    private val stateBanknoteVisibility = MutableStateFlow(false)

    override suspend fun getBanknotes(): List<Banknote> {
        return banknotesDao.getBanknotes().map { it.toBanknote() }
    }

    override suspend fun getVisibleBanknotes(): List<DetailedBanknote> {
        return banknotesDao.getBanknotesByVisibility().map { it.toDetailedBanknote() }
    }

    override suspend fun updateBanknoteVisibility(banknote: Banknote) {
        banknotesDao.updateBanknoteVisibility(banknote.isShow, banknote.id.value.toInt())
        stateBanknoteVisibility.value = true
    }

    override fun isBanknotesVisibilityChanged(): Boolean {
        return stateBanknoteVisibility.value
    }

    override fun resetBanknotesVisibilityChangedSettings() {
        stateBanknoteVisibility.value = false
    }
}