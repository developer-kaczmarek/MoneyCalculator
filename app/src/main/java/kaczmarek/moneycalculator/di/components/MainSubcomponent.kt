package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.MainModule
import kaczmarek.moneycalculator.di.scopes.MainScope
import kaczmarek.moneycalculator.ui.main.PresenterMain
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainSubcomponent {
    fun inject(presenter: PresenterMain)
}
