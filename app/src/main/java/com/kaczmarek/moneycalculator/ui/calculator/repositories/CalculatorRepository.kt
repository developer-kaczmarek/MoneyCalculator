package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.BanknoteDao
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.SessionDao
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class CalculatorRepository(private val databaseService: DatabaseService, private val settingsService: SettingsService) : ICalculatorRepository {

    override fun getAll() = databaseService.banknoteDao().getAll()

    override suspend fun saveSession(session: Session) {
        databaseService.sessionDao().save(session)
    }

    override fun getKeyboardLayout() = settingsService.keyboardLayout

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

}