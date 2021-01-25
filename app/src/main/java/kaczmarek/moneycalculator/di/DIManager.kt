package kaczmarek.moneycalculator.di

import kaczmarek.moneycalculator.di.components.*
import kaczmarek.moneycalculator.di.modules.CalculatorFragmentModule
import kaczmarek.moneycalculator.di.modules.HistoryFragmentModule
import kaczmarek.moneycalculator.di.modules.MainActivityModule
import kaczmarek.moneycalculator.di.modules.SettingsFragmentModule
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
                appComponent.calculator(CalculatorFragmentModule())
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
                appComponent.history(HistoryFragmentModule())
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
                appComponent.settings(SettingsFragmentModule())
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
                appComponent.main(MainActivityModule())
        }
        return mainSubcomponent
            ?: throw IllegalStateException("$mainSubcomponent must not be null")
    }

    fun removeMainSubcomponent() {
        mainSubcomponent = null
    }
}