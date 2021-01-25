package kaczmarek.moneycalculator.domain.session.usecase

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class SaveSessionUseCase(private val repository: ISessionRepository) {

    suspend fun save(session: SessionEntity) {
        repository.saveCurrentSession(session)
    }

}