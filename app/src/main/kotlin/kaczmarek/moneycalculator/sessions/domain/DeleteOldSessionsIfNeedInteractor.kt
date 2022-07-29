package kaczmarek.moneycalculator.sessions.domain

import kaczmarek.moneycalculator.core.utils.getFormattedDateAfterDaysSubtracted
import kaczmarek.moneycalculator.sessions.data.SessionsStorage
import kaczmarek.moneycalculator.settings.data.SettingsStorage
import kaczmarek.moneycalculator.settings.domain.Settings

class DeleteOldSessionsIfNeedInteractor(
    private val sessionsStorage: SessionsStorage,
    private val settingsStorage: SettingsStorage
) {

    suspend fun execute() {
        when (settingsStorage.getHistoryStoragePeriod()) {
            Settings.HistoryStoragePeriod.FourteenDays -> {
                sessionsStorage.deleteSessionsByDate(getFormattedDateAfterDaysSubtracted(days = 14))
            }

            Settings.HistoryStoragePeriod.ThirtyDays -> {
                sessionsStorage.deleteSessionsByDate(getFormattedDateAfterDaysSubtracted(days = 30))
            }

            else -> Unit
        }
    }
}