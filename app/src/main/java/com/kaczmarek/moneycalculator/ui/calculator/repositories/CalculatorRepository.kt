package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.database.BanknoteDao
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class CalculatorRepository(databaseService: DatabaseService) : ICalculatorRepository {
    private val banknoteDao: BanknoteDao = databaseService.banknoteDao()

    override fun getAll() = banknoteDao.getAll()

}