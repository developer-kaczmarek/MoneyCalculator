package kaczmarek.moneycalculator

import kaczmarek.moneycalculator.app_theme.appThemeModule
import kaczmarek.moneycalculator.calculator.calculatorModule
import kaczmarek.moneycalculator.core.coreModule
import kaczmarek.moneycalculator.sessions.sessionsModule
import kaczmarek.moneycalculator.settings.settingsModule

val allModules = listOf(
    coreModule,
    appThemeModule,
    calculatorModule,
    sessionsModule,
    settingsModule
)
