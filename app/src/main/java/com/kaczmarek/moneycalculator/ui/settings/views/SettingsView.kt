package com.kaczmarek.moneycalculator.ui.settings.views

import com.kaczmarek.moneycalculator.ui.base.views.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface SettingsView : BaseView {
    fun showMessage(message: String)
    fun loadBanknotes()
}