package kaczmarek.moneycalculator.core.domain

import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesStorage

class ChangeBanknoteVisibilityInteractor(private val banknotesStorage: BanknotesStorage) {

    suspend fun execute(banknote: Banknote) {
        banknotesStorage.updateBanknoteVisibility(banknote)
    }
}