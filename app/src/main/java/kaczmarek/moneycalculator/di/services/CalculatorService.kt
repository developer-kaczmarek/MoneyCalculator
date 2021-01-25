package kaczmarek.moneycalculator.di.services

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

class CalculatorService {

    /**
     * Список всех банкнот
     */
    private val calculatorItems = arrayListOf<BanknoteEntity>()

    fun getCalculatorItems() = calculatorItems

    fun setCalculatorItems(banknotes: List<BanknoteEntity>) {
        calculatorItems.clear()
        calculatorItems.addAll(banknotes)
    }
}