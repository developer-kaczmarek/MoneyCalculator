package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class SetAlwaysBacklightOnUseCase(private val repository: ISettingsRepository) {

    fun setAlwaysBacklight(isOn: Boolean) {
        repository.setAlwaysBacklightOn(isOn)
    }

}