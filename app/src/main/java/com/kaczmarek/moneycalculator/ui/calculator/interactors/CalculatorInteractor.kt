package com.kaczmarek.moneycalculator.ui.calculator.interactors

import com.kaczmarek.moneycalculator.ui.calculator.repositories.ICalculatorRepository

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class CalculatorInteractor(private val repository: ICalculatorRepository) {

    fun getAll() = repository.getAll()
}