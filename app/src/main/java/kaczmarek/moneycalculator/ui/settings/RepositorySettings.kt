package kaczmarek.moneycalculator.ui.settings

import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.ui.settings.IRepositorySettings

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RepositorySettings(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService
) : IRepositorySettings {
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

    override suspend fun updateBanknote(banknote: Banknote) =
        databaseService.banknoteDao().updateBanknote(banknote)

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

    override fun getKeyboardLayout() = settingsService.keyboardLayout

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

    override fun getCountMeetComponents() = settingsService.countMeetComponents

    override fun updateCountMeetComponent(countMeetComponent: Int) {
        settingsService.countMeetComponents = countMeetComponent
    }
}