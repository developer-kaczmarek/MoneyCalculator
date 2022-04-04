package kaczmarek.moneycalculator.core.data.storage.banknotes

import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.domain.DetailedBanknote

interface BanknotesStorage {

    suspend fun getBanknotes(): List<Banknote>

    suspend fun getVisibleBanknotes(): List<DetailedBanknote>

    suspend fun updateBanknoteVisibility(banknote: Banknote)
}