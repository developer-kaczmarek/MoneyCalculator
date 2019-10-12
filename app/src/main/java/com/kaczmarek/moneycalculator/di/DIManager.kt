package com.kaczmarek.moneycalculator.di

import com.kaczmarek.moneycalculator.di.components.AppComponent
import com.kaczmarek.moneycalculator.di.components.CalculatorSubcomponent
import com.kaczmarek.moneycalculator.di.modules.CalculatorModule

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
object DIManager {
    lateinit var appComponent: AppComponent

    private var calculatorSubcomponent: CalculatorSubcomponent? = null

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
}