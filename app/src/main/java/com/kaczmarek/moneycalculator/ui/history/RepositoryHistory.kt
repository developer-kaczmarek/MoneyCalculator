package com.kaczmarek.moneycalculator.ui.history

import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.history.IRepositoryHistory

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RepositoryHistory(private val databaseService: DatabaseService) :
    IRepositoryHistory {
    override suspend fun getAll() = databaseService.sessionDao().getAll()
}