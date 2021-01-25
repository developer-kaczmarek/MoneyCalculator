package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.SettingsFragmentModule
import kaczmarek.moneycalculator.di.scopes.SettingsFragmentScope
import kaczmarek.moneycalculator.ui.settings.SettingsPresenter
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@SettingsFragmentScope
@Subcomponent(modules = [SettingsFragmentModule::class])
interface SettingsSubcomponent {
    fun inject(presenter: SettingsPresenter)
}
