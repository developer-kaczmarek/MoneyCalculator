package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class GetKeyboardLayoutUseCase(private val repository: ISettingsRepository) {
    fun invoke(): Int {
        return repository.getKeyboardLayout()
    }
}