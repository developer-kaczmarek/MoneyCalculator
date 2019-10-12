package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
interface ICalculatorRepository {
    fun getAll(): List<Banknote>
    suspend fun saveSession(session: Session)

}
