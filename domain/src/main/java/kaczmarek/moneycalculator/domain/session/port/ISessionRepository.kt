package kaczmarek.moneycalculator.domain.session.port

import kaczmarek.moneycalculator.domain.session.entity.SessionEntity


interface ISessionRepository {

    suspend fun deleteSessionsByDate(date: String)

    suspend fun getSessions(): List<SessionEntity>

    suspend fun saveCurrentSession(sessionEntity: SessionEntity)

    suspend fun deleteSession(sessionEntity: SessionEntity)

}