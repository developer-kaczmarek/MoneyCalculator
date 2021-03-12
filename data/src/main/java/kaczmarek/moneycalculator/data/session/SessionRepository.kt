package kaczmarek.moneycalculator.data.session

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class SessionRepository(private val database: ISessionDatabase) : ISessionRepository {
    override suspend fun deleteSessionsByDate(date: String) {
        database.deleteSessionByDate(date)
    }

    override suspend fun getSessions(): List<SessionEntity> = database.getSessions()

    override suspend fun saveCurrentSession(
        date: String,
        time: String,
        totalAmount: Double,
        banknotes: List<BanknoteEntity>
    ) {
        database.insert(SessionEntity(date, time, totalAmount, banknotes, null))
    }

    override suspend fun deleteSession(sessionEntity: SessionEntity) {
        database.delete(sessionEntity)
    }
}