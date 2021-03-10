package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class SaveSessionUseCase(private val repository: ISessionRepository) {
    suspend fun invoke(
        date: String,
        time: String,
        totalAmount: Double,
        banknotes: List<BanknoteEntity>
    ) {
        repository.saveCurrentSession(date, time, totalAmount, banknotes)
    }
}