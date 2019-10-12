package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.database.BanknoteDao
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.di.services.database.SessionDao
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class CalculatorRepository(databaseService: DatabaseService) : ICalculatorRepository {
    private val banknoteDao: BanknoteDao = databaseService.banknoteDao()
    private val sessionDao: SessionDao = databaseService.sessionDao()

    override fun getAll() = banknoteDao.getAll()

    override suspend fun saveSession(session: Session) {
        sessionDao.save(session)
    }

}