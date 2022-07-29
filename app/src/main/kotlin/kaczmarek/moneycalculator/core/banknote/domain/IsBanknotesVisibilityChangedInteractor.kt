package kaczmarek.moneycalculator.core.banknote.domain

import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorage

class IsBanknotesVisibilityChangedInteractor(private val banknotesStorage: BanknotesStorage) {

    fun execute(): Boolean {
        return banknotesStorage.isBanknotesVisibilityChanged()
    }
}