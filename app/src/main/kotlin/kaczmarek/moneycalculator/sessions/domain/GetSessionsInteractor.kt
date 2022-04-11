package kaczmarek.moneycalculator.sessions.domain

import kaczmarek.moneycalculator.sessions.data.SessionsStorage

class GetSessionsInteractor(private val sessionsStorage: SessionsStorage) {

    suspend fun execute(): List<Session> {
        return sessionsStorage.getSessions()
    }
}