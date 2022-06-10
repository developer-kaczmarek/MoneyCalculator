package kaczmarek.moneycalculator.settings

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.createPreferences
import kaczmarek.moneycalculator.settings.data.SettingsStorage
import kaczmarek.moneycalculator.settings.data.SettingsStorageImpl
import kaczmarek.moneycalculator.settings.domain.*
import kaczmarek.moneycalculator.settings.ui.RealSettingsComponent
import kaczmarek.moneycalculator.settings.ui.SettingsComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val settingsPrefsName = "SETTINGS"

val settingsModule = module {
    single(named(settingsPrefsName)) { createPreferences(androidContext(), settingsPrefsName) }
    single<SettingsStorage> { SettingsStorageImpl(get(named(settingsPrefsName))) }
    factory { GetSettingsInteractor(get(), get()) }
    factory { ChangeKeyboardLayoutTypeInteractor(get()) }
    factory { ChangeHistoryStoragePeriodInteractor(get()) }
    factory { ChangeKeepScreenOnInteractor(get()) }
    factory { ChangeThemeTypeInteractor(get()) }
    factory { IsSettingsChangedInteractor(get()) }
}

fun ComponentFactory.createSettingsComponent(
    componentContext: ComponentContext,
    onOutput: (SettingsComponent.Output) -> Unit
): SettingsComponent {
    return RealSettingsComponent(
        componentContext,
        onOutput,
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get()
    )
}