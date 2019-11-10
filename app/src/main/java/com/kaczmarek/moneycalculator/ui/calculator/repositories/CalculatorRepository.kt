package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.CalculatorService
import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class CalculatorRepository(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService,
    private val calculatorService: CalculatorService) : ICalculatorRepository {

    override fun getAll() = databaseService.banknoteDao().getAll()

    override suspend fun saveSession(session: Session) {
        databaseService.sessionDao().save(session)
    }

    override fun getKeyboardLayout() = settingsService.keyboardLayout

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

    override fun setCalculatorItems(banknotes: List<Banknote>) = calculatorService.setCalculatorItems(banknotes)

    override fun getCalculatorItems() = calculatorService.getCalculatorItems()

}