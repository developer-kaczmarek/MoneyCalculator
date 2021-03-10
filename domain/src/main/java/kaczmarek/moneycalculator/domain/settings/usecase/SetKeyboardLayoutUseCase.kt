package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class SetKeyboardLayoutUseCase(private val repository: ISettingsRepository) {
    fun invoke(type: Int) {
        repository.setKeyboardLayout(type)
    }
}