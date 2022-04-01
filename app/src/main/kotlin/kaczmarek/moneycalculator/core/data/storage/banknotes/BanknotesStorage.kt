package kaczmarek.moneycalculator.core.data.storage.banknotes

import kaczmarek.moneycalculator.core.domain.Banknote

interface BanknotesStorage {

    suspend fun getBanknotes(): List<Banknote>

    suspend fun updateBanknoteVisibility(banknote: Banknote)
}