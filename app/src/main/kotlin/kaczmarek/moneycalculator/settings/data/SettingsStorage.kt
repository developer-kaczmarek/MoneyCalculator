package kaczmarek.moneycalculator.settings.data

import kaczmarek.moneycalculator.settings.domain.Settings

interface SettingsStorage {

    fun getKeyboardLayoutType(): Settings.KeyboardLayoutType

    fun updateKeyboardLayoutType(keyboardLayoutType: Settings.KeyboardLayoutType)

    fun getHistoryStoragePeriod(): Settings.HistoryStoragePeriod

    fun updateHistoryStoragePeriod(historyStoragePeriod: Settings.HistoryStoragePeriod)

    fun isDisplayAlwaysOn(): Boolean

    fun updateDisplayAlwaysOn(checked: Boolean)

    fun getThemeType(): Settings.ThemeType

    fun updateThemeType(themeType: Settings.ThemeType)
}