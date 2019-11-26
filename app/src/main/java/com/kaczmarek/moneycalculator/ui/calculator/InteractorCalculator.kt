package com.kaczmarek.moneycalculator.ui.calculator

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.di.services.database.models.Session
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class InteractorCalculator(private val repository: IRepositoryCalculator) {

    fun getAll() = repository.getAll()

    suspend fun saveSession(totalAmount: Float, completedBanknotes: List<Banknote>) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val session = Session(currentDate, currentTime, totalAmount, completedBanknotes)
        repository.saveSession(session)
    }

    fun getKeyboardLayout() = repository.getKeyboardLayout()

    fun setCalculatorItems(banknotes: List<Banknote>) = repository.setCalculatorItems(banknotes)

    fun getCalculatorItems() = repository.getCalculatorItems()
}