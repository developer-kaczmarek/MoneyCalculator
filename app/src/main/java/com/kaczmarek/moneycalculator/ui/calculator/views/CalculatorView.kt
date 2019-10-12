package com.kaczmarek.moneycalculator.ui.calculator.views

import com.kaczmarek.moneycalculator.ui.base.views.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CalculatorView : BaseView{
    fun addBanknoteCard()
    fun updateTotalAmount()
    fun showMessage(message: String)

}