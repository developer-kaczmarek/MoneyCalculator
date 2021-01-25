package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.CalculatorFragmentModule
import kaczmarek.moneycalculator.di.scopes.CalculatorFragmentScope
import kaczmarek.moneycalculator.ui.calculator.CalculatorPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@CalculatorFragmentScope
@Subcomponent(modules = [CalculatorFragmentModule::class])
interface CalculatorSubcomponent {
    fun inject(presenter: CalculatorPresenter)
}
