package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class GetHistoryStoragePeriodUseCase(private val repository: ISettingsRepository) {
    fun invoke(): Int {
        return repository.getHistoryStoragePeriod()
    }
}