package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.di.services.database.DatabaseService

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class RepositoryHistory(private val databaseService: DatabaseService) :
    IRepositoryHistory {
    override fun getAll() = databaseService.sessionDao().getAll()
}