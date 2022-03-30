package kaczmarek.moneycalculator.settings

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.settings.ui.RealSettingsComponent
import kaczmarek.moneycalculator.settings.ui.SettingsComponent
import org.koin.dsl.module

val settingsModule = module {

}

fun ComponentFactory.createSettingsComponent(
    componentContext: ComponentContext
): SettingsComponent {
    return RealSettingsComponent(componentContext)
}