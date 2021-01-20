package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.HistoryModule
import kaczmarek.moneycalculator.di.scopes.HistoryScope
import dagger.Subcomponent
import kaczmarek.moneycalculator.ui.history.HistoryPresenter

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@HistoryScope
@Subcomponent(modules = [HistoryModule::class])
interface HistorySubcomponent {
    fun inject(presenter: HistoryPresenter)
}
