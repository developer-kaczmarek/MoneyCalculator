package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class DeleteSessionUseCase(private val repository: ISessionRepository) {

    suspend fun deleteByDate(date: String) {
        repository.deleteSessionsByDate(date)
    }

    suspend fun delete(session: SessionEntity) {
        repository.deleteSession(session)
    }

}