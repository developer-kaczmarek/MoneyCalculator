package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class ChangeDisplayAlwaysOnInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(checked: Boolean) {
        settingsStorage.updateDisplayAlwaysOn(checked)
    }
}