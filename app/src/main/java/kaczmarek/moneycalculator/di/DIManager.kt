package kaczmarek.moneycalculator.di

import kaczmarek.moneycalculator.di.components.*
import kaczmarek.moneycalculator.di.modules.CalculatorModule
import kaczmarek.moneycalculator.di.modules.HistoryModule
import kaczmarek.moneycalculator.di.modules.MainModule
import kaczmarek.moneycalculator.di.modules.SettingsModule
import kaczmarek.moneycalculator.di.components.AppComponent

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
object DIManager {
    lateinit var appComponent: AppComponent

    private var calculatorSubcomponent: CalculatorSubcomponent? = null

    private var historySubcomponent: HistorySubcomponent? = null

    private var settingsSubcomponent: SettingsSubcomponent? = null

    private var mainSubcomponent: MainSubcomponent? = null

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

    fun getSettingsSubcomponent(): SettingsSubcomponent {
        if (settingsSubcomponent == null) {
            settingsSubcomponent =
                appComponent.settings(SettingsModule())
        }
        return settingsSubcomponent
            ?: throw IllegalStateException("$settingsSubcomponent must not be null")
    }

    fun removeSettingsSubcomponent() {
        historySubcomponent = null
    }

    fun getMainSubcomponent(): MainSubcomponent {
        if (mainSubcomponent == null) {
            mainSubcomponent =
                appComponent.main(MainModule())
        }
        return mainSubcomponent
            ?: throw IllegalStateException("$mainSubcomponent must not be null")
    }

    fun removeMainSubcomponent() {
        mainSubcomponent = null
    }
}