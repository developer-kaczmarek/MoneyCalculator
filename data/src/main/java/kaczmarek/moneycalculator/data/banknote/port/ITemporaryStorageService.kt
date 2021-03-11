package kaczmarek.moneycalculator.data.banknote.port

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

interface ITemporaryStorageService {
    fun saveBanknotesFromCurrentSession(banknotes: List<BanknoteEntity>)
    fun getBanknotesIntoCurrentSession(): List<BanknoteEntity>
}