package kaczmarek.moneycalculator.core.banknote.data

import kaczmarek.moneycalculator.core.banknote.domain.Banknote
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote

interface BanknotesStorage {

    suspend fun getBanknotes(): List<Banknote>

    suspend fun getVisibleBanknotes(): List<DetailedBanknote>

    suspend fun updateBanknoteVisibility(banknote: Banknote)
}