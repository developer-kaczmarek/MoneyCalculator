package kaczmarek.moneycalculator.core.banknote.domain

import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorage

class ChangeBanknoteVisibilityInteractor(private val banknotesStorage: BanknotesStorage) {

    suspend fun execute(banknote: Banknote) {
        banknotesStorage.updateBanknoteVisibility(banknote)
    }
}