package com.kaczmarek.moneycalculator.ui.history.views

import com.kaczmarek.moneycalculator.ui.base.views.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface HistoryView : BaseView {
    fun showMessage(message: String)
    fun updateSessions()

}