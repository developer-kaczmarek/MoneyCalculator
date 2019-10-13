package com.kaczmarek.moneycalculator.di

import com.kaczmarek.moneycalculator.di.components.AppComponent
import com.kaczmarek.moneycalculator.di.components.CalculatorSubcomponent
import com.kaczmarek.moneycalculator.di.components.HistorySubcomponent
import com.kaczmarek.moneycalculator.di.modules.CalculatorModule
import com.kaczmarek.moneycalculator.di.modules.HistoryModule

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
object DIManager {
    lateinit var appComponent: AppComponent

    private var calculatorSubcomponent: CalculatorSubcomponent? = null

    private var historySubcomponent: HistorySubcomponent? = null

    fun getCalculatorSubcomponent(): CalculatorSubcomponent {
        if (calculatorSubcomponent == null) {
            calculatorSubcomponent =
                appComponent.calculator(CalculatorModule())
        }
        return calculatorSubcomponent
            ?: throw IllegalStateException("$calculatorSubcomponent must not be null")
    }

    fun removeCalculatorSubcomponent() {
        calculatorSubcomponent = null
    }

    fun getHistorySubcomponent(): HistorySubcomponent {
        if (historySubcomponent == null) {
            historySubcomponent =
                appComponent.history(HistoryModule())
        }
        return historySubcomponent
            ?: throw IllegalStateException("$calculatorSubcomponent must not be null")
    }

    fun removeHistorySubcomponent() {
        historySubcomponent = null
    }
}