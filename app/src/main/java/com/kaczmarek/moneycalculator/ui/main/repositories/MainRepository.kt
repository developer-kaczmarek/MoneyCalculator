package com.kaczmarek.moneycalculator.ui.main.repositories

import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.models.Session

class MainRepository(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService
) : IMainRepository {
    override suspend fun deleteSession(session: Session) {
        databaseService.sessionDao().deleteSession(session)
    }

    override suspend fun getAll() = databaseService.sessionDao().getAll()

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

}