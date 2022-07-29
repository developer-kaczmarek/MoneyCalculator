package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class ChangeThemeTypeInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(themeType: Settings.ThemeType) {
        settingsStorage.updateThemeType(themeType)
    }
}