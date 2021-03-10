package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class DeleteSessionsByDateUseCase(private val repository: ISessionRepository) {
    suspend fun invoke(date: String) {
        repository.deleteSessionsByDate(date)
    }
}