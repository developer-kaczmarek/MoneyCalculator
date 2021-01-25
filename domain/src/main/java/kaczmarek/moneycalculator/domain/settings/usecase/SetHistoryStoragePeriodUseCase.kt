package kaczmarek.moneycalculator.domain.settings.usecase

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class SetHistoryStoragePeriodUseCase(private val repository: ISettingsRepository) {

    fun setPeriod(period: Int) {
        repository.setHistoryStoragePeriod(period)
    }

}