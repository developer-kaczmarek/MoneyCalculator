package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.settings.data.SettingsStorage

class IsSettingsChangedInteractor(private val settingsStorage: SettingsStorage) {

    fun execute(): Boolean {
        return settingsStorage.isSettingsChanged()
    }
}