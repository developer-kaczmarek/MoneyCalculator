package com.kaczmarek.moneycalculator.ui.base.views

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView