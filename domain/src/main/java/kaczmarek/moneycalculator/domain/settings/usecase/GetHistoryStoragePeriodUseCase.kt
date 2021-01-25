package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class GetHistoryStoragePeriodUseCase(private val repository: ISettingsRepository) {

    fun getPeriod(): Int {
        return repository.getHistoryStoragePeriod()
    }

}