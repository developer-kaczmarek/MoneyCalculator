package kaczmarek.moneycalculator.di.components

import kaczmarek.moneycalculator.di.modules.SettingsModule
import kaczmarek.moneycalculator.di.scopes.SettingsScope
import kaczmarek.moneycalculator.ui.settings.PresenterSettings
import dagger.Subcomponent

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@SettingsScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsSubcomponent {
    fun inject(presenter: PresenterSettings)
}
