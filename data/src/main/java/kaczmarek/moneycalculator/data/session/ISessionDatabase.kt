package kaczmarek.moneycalculator.data.session

import kaczmarek.moneycalculator.data.base.port.ICrudRepository
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity

interface ISessionDatabase : ICrudRepository<SessionEntity> {
    suspend fun getSessions(): List<SessionEntity>
    suspend fun deleteSessionByDate(date: String)
}