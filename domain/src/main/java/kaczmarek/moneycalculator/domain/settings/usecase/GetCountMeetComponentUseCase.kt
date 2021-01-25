package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class GetCountMeetComponentUseCase(private val repository: ISettingsRepository) {

    fun getCount(): Int {
        return repository.getCountMeetComponents()
    }

}