package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class DeleteSessionByModelUseCase(private val repository: ISessionRepository) {
    suspend fun invoke(session: SessionEntity) {
        repository.deleteSession(session)
    }
}