package kaczmarek.moneycalculator.sessions.data

import kaczmarek.moneycalculator.core.domain.DetailedBanknote
import kaczmarek.moneycalculator.sessions.domain.Session

interface SessionsStorage {

    suspend fun getSessions(): List<Session>

    suspend fun deleteSession(session: Session)

    suspend fun saveSession(
        date: String,
        time: String,
        totalAmount: Double,
        banknotes: List<DetailedBanknote>
    )

    suspend fun deleteSessionsByDate(date: String)
}