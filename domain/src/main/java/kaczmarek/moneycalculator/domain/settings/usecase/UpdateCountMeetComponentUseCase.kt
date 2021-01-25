package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class UpdateCountMeetComponentUseCase(private val repository: ISettingsRepository) {

    fun updateCount(count: Int) {
        repository.updateCountMeetComponent(count)
    }

}