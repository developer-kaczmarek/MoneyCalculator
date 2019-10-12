package com.kaczmarek.moneycalculator.ui.calculator.repositories

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
interface ICalculatorRepository {
    fun getAll(): List<Banknote>

}
