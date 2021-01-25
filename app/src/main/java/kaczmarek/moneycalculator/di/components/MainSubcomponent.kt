package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.MainActivityModule
import kaczmarek.moneycalculator.di.scopes.MainActivityScope
import kaczmarek.moneycalculator.ui.main.MainPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@MainActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainSubcomponent {
    fun inject(presenter: MainPresenter)
}
