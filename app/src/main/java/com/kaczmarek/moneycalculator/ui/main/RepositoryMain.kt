package com.kaczmarek.moneycalculator.ui.main

import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.models.Session
import com.kaczmarek.moneycalculator.ui.main.IRepositoryMain

class RepositoryMain(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService
) : IRepositoryMain {
    override suspend fun deleteSession(session: Session) {
        databaseService.sessionDao().deleteSession(session)
    }

    override suspend fun getAll() = databaseService.sessionDao().getAll()

    override fun getHistoryStoragePeriod() = settingsService.historyStoragePeriod

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

}