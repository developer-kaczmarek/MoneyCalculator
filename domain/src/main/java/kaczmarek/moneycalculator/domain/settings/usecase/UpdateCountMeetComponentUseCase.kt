package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class UpdateCountMeetComponentUseCase(private val repository: ISettingsRepository) {
    fun invoke(count: Int) {
        repository.updateCountMeetComponent(count)
    }
}