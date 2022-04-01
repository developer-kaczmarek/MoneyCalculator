package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class ChangeHistoryStoragePeriodInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(period: Settings.HistoryStoragePeriod) {
        settingsStorage.updateHistoryStoragePeriod(period)
    }
}