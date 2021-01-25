package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class GetSessionUseCase(private val repository: ISessionRepository) {

    suspend fun getList(): List<SessionEntity> {
        return repository.getSessions()
    }

}