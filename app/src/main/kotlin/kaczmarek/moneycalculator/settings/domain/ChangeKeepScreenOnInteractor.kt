package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class ChangeKeepScreenOnInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(checked: Boolean) {
        settingsStorage.updateKeepScreenOn(checked)
    }
}