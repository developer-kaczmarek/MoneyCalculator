package kaczmarek.moneycalculator.core.banknote.domain

import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorage

class GetBanknotesInteractor(private val banknotesStorage: BanknotesStorage) {

    suspend fun execute(): List<Banknote> {
        return banknotesStorage.getBanknotes()
    }
}