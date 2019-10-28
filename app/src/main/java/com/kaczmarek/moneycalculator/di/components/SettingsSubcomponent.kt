package com.kaczmarek.moneycalculator.di.components

import com.kaczmarek.moneycalculator.di.modules.SettingsModule
import com.kaczmarek.moneycalculator.di.scopes.SettingsScope
import com.kaczmarek.moneycalculator.ui.settings.presenters.SettingsPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@SettingsScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsSubcomponent {
    fun inject(presenter: SettingsPresenter)
}
