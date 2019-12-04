package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.CalculatorModule
import kaczmarek.moneycalculator.di.scopes.CalculatorScope
import kaczmarek.moneycalculator.ui.calculator.PresenterCalculator
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@CalculatorScope
@Subcomponent(modules = [CalculatorModule::class])
interface CalculatorSubcomponent {
    fun inject(presenter: PresenterCalculator)
}
