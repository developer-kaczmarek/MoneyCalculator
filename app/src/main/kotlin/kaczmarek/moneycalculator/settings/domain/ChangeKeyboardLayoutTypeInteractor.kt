package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class ChangeKeyboardLayoutTypeInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(type: Settings.KeyboardLayoutType) {
        settingsStorage.updateKeyboardLayoutType(type)
    }
}