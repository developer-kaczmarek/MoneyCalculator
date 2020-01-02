package kaczmarek.moneycalculator.ui.calculator

import kaczmarek.moneycalculator.di.services.CalculatorService
import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class RepositoryCalculator(
    private val databaseService: DatabaseService,
    private val settingsService: SettingsService,
    private val calculatorService: CalculatorService
) : IRepositoryCalculator {

    override fun getAll() = databaseService.banknoteDao().getAll()

    override fun saveSession(session: Session) {
        databaseService.sessionDao().save(session)
    }

    override fun getKeyboardLayout() = settingsService.keyboardLayout

    override fun getCountMeetComponents() = settingsService.countMeetComponents

    override fun updateCountMeetComponent(countMeetComponent: Int) {
        settingsService.countMeetComponents = countMeetComponent
    }

    override fun setCalculatorItems(banknotes: List<Banknote>) =
        calculatorService.setCalculatorItems(banknotes)

    override fun getCalculatorItems() = calculatorService.getCalculatorItems()

    override fun isAlwaysOnDisplay() = settingsService.isAlwaysOnDisplay

}