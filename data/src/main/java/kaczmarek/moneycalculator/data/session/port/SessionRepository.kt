package kaczmarek.moneycalculator.data.session.port

import kaczmarek.moneycalculator.data.session.mapper.SessionEntityMapper
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import kaczmarek.moneycalculator.domain.session.port.ISessionRepository

class SessionRepository(
    private val database: ISessionDatabase,
    private val mapper: SessionEntityMapper
) : ISessionRepository {

    override suspend fun deleteSessionsByDate(date: String) {
        database.deleteSessionByDate(date)
    }

    override suspend fun getSessions(): List<SessionEntity> =
        database.getSessions().map { mapper.mapToEntity(it) }.toList()

    override suspend fun saveCurrentSession(date: String, time: String, totalAmount: Double, banknotes: List<BanknoteEntity>) {
        database.insert(mapper.mapFromEntity(SessionEntity(date, time, totalAmount, banknotes)))
    }

    override suspend fun deleteSession(sessionEntity: SessionEntity) {
        database.delete(mapper.mapFromEntity(sessionEntity))
    }

}