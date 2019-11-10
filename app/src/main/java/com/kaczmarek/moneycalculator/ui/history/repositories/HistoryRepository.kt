package com.kaczmarek.moneycalculator.ui.history.repositories

import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class HistoryRepository(private val databaseService: DatabaseService, private val settingsService: SettingsService) : IHistoryRepository {
    override suspend fun deleteSession(session: Session) {
       databaseService.sessionDao().deleteSession(session)
    }

    override suspend fun getAll() = databaseService.sessionDao().getAll()

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

}