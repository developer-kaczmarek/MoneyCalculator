package kaczmarek.moneycalculator.sessions.data

import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId

interface SessionsStorage {

    suspend fun getSessions(): List<Session>

    suspend fun deleteSessionById(id: SessionId)

    suspend fun saveSession(
        date: String,
        time: String,
        totalAmount: Double,
        banknotes: List<DetailedBanknote>
    )

    suspend fun deleteSessionsByDate(date: String)
}