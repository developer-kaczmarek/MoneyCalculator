package com.kaczmarek.moneycalculator.di.components

import com.kaczmarek.moneycalculator.di.modules.HistoryModule
import com.kaczmarek.moneycalculator.di.scopes.HistoryScope
import com.kaczmarek.moneycalculator.ui.history.presenters.HistoryPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@HistoryScope
@Subcomponent(modules = [HistoryModule::class])
interface HistorySubcomponent {
    fun inject(presenter: HistoryPresenter)
}
