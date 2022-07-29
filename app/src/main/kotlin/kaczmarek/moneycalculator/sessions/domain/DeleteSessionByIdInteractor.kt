package kaczmarek.moneycalculator.sessions.domain

import kaczmarek.moneycalculator.sessions.data.SessionsStorage

class DeleteSessionByIdInteractor(private val sessionsStorage: SessionsStorage) {

    suspend fun execute(id: SessionId) {
        sessionsStorage.deleteSessionById(id)
    }
}