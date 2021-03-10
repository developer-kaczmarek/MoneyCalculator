package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class GetSessionsListUseCase(private val repository: ISessionRepository) {
    suspend fun invoke(): List<SessionEntity> {
        return repository.getSessions()
    }
}