package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class SetHistoryStoragePeriodUseCase(private val repository: ISettingsRepository) {
    fun invoke(period: Int) {
        repository.setHistoryStoragePeriod(period)
    }
}