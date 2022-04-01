package kaczmarek.moneycalculator.core.domain

import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesStorage

class GetBanknotesInteractor(private val banknotesStorage: BanknotesStorage) {

    suspend fun execute(): List<Banknote> {
        return banknotesStorage.getBanknotes()
    }
}