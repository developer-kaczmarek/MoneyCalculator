package com.kaczmarek.moneycalculator.ui.settings.repositories

import com.kaczmarek.moneycalculator.di.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class SettingsRepository(private val databaseService: DatabaseService, private val settingsService: SettingsService) : ISettingsRepository {
    override fun saveAlwaysOnDisplay(isSelected: Boolean) {
        settingsService.isAlwaysOnDisplay = isSelected
    }

    override fun saveKeyboardLayout(setting: Int) {
        settingsService.keyboardLayout = setting
    }

    override fun saveHistoryStoragePeriod(setting: Int) {
        settingsService.historyStoragePeriod = setting
    }

    override suspend fun getAllBanknotes(): List<Banknote> = databaseService.banknoteDao().getAll()

    override fun updateBanknote(banknote: Banknote) = databaseService.banknoteDao().updateBanknote(banknote)

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

    override fun getKeyboardLayout() = settingsService.keyboardLayout

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay
}