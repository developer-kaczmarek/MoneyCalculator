package kaczmarek.moneycalculator.di.services

import kaczmarek.moneycalculator.data.banknote.port.ITemporaryStorageService
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

class TemporaryStorageService: ITemporaryStorageService {

    /**
     * Список всех банкнот текущего сеанса
     */
    private val currentSessionItems = arrayListOf<BanknoteEntity>()

    override fun getBanknotesIntoCurrentSession(): List<BanknoteEntity> {
        return currentSessionItems
    }

    override fun saveBanknotesFromCurrentSession(banknotes: List<BanknoteEntity>) {
        currentSessionItems.apply {
            clear()
            addAll(banknotes)
        }
    }
}