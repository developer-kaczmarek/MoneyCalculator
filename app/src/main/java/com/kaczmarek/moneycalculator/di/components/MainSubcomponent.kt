package com.kaczmarek.moneycalculator.di.components

import com.kaczmarek.moneycalculator.di.modules.MainModule
import com.kaczmarek.moneycalculator.di.scopes.MainScope
import com.kaczmarek.moneycalculator.ui.main.PresenterMain
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainSubcomponent {
    fun inject(presenter: PresenterMain)
}
