package com.kaczmarek.moneycalculator.di.components

import com.kaczmarek.moneycalculator.di.modules.CalculatorModule
import com.kaczmarek.moneycalculator.di.scopes.CalculatorScope
import com.kaczmarek.moneycalculator.ui.calculator.presenters.CalculatorPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@CalculatorScope
@Subcomponent(modules = [CalculatorModule::class])
interface CalculatorSubcomponent {
    fun inject(presenter: CalculatorPresenter)
}
