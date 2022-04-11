package kaczmarek.moneycalculator.sessions.data

import kaczmarek.moneycalculator.core.domain.DetailedBanknote
import kaczmarek.moneycalculator.sessions.domain.Session

class SessionsStorageImpl(private val sessionsDao: SessionsDao) : SessionsStorage {

    override suspend fun getSessions(): List<Session> {
        return sessionsDao.getSessions().map { it.toSession() }
    }

    override suspend fun deleteSession(session: Session) {
        sessionsDao.deleteSessionById(session.id.value.toInt())
    }

    override suspend fun saveSession(
        date: String,
        time: String,
        totalAmount: Double,
        banknotes: List<DetailedBanknote>
    ) {
        sessionsDao.saveSession(
            SessionDbModel(
                date = date,
                time = time,
                totalAmount = totalAmount,
                banknotes = banknotes
            )
        )
    }

    override suspend fun deleteSessionsByDate(date: String) {
        sessionsDao.deleteSessionsByDate(date)
    }
}