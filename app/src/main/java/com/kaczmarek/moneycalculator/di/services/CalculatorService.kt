package com.kaczmarek.moneycalculator.di.services

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

class CalculatorService {

    /**
     * Список всех банкнот
     */
    private val calculatorItems = arrayListOf<Banknote>()

    fun getCalculatorItems() = calculatorItems

    fun setCalculatorItems(banknotes: List<Banknote>) {
        calculatorItems.clear()
        calculatorItems.addAll(banknotes)
    }
}