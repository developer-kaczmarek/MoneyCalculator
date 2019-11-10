package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
interface ICalculatorRepository {
    fun getAll(): List<Banknote>
    suspend fun saveSession(session: Session)
    fun getKeyboardLayout(): Int
    fun isAlwaysOnDisplay(): Boolean
    fun setCalculatorItems(banknotes: List<Banknote>)
    fun getCalculatorItems(): List<Banknote>

}
