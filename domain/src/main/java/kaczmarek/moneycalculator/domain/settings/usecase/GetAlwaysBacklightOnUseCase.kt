package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class GetAlwaysBacklightOnUseCase(private val repository: ISettingsRepository) {
    fun invoke(): Boolean {
        return repository.isAlwaysBacklightOn()
    }
}