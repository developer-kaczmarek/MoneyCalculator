package com.kaczmarek.moneycalculator.ui.history.repositories

import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.SessionDao

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class HistoryRepository(databaseService: DatabaseService) : IHistoryRepository {
    private val sessionDao: SessionDao = databaseService.sessionDao()

    override suspend fun getAll() = sessionDao.getAll()

}