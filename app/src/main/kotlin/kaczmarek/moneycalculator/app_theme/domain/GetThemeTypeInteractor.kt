package kaczmarek.moneycalculator.app_theme.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage
import kaczmarek.moneycalculator.settings.domain.Settings

class GetThemeTypeInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(): Settings.ThemeType {
        return settingsStorage.getThemeType()
    }
}