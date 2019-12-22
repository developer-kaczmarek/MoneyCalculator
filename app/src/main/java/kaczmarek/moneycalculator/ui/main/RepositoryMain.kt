package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.di.services.database.models.Session
import kaczmarek.moneycalculator.ui.main.IRepositoryMain

class RepositoryMain(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService
) : IRepositoryMain {
    override suspend fun deleteSession(session: Session) {
        databaseService.sessionDao().deleteSession(session)
    }

    override suspend fun getAll() = databaseService.sessionDao().getAll()

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

}