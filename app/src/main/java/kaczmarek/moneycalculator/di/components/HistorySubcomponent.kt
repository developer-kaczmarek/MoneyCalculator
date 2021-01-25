package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.HistoryFragmentModule
import kaczmarek.moneycalculator.di.scopes.HistoryFragmentScope
import dagger.Subcomponent
import kaczmarek.moneycalculator.ui.history.HistoryPresenter

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@HistoryFragmentScope
@Subcomponent(modules = [HistoryFragmentModule::class])
interface HistorySubcomponent {
    fun inject(presenter: HistoryPresenter)
}
