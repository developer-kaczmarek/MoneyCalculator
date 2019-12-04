package kaczmarek.moneycalculator.ui.calculator

import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
interface IRepositoryCalculator {
    fun getAll(): List<Banknote>
    suspend fun saveSession(session: Session)
    fun getKeyboardLayout(): Int
    fun setCalculatorItems(banknotes: List<Banknote>)
    fun getCalculatorItems(): List<Banknote>
    fun getCountMeetComponents(): Int
    fun updateCountMeetComponent(countMeetComponent: Int)

}
