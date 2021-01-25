package kaczmarek.moneycalculator.data.session.port

import kaczmarek.moneycalculator.data.base.port.ICrudRepository
import kaczmarek.moneycalculator.data.session.model.Session

interface ISessionDatabase : ICrudRepository<Session> {

    suspend fun getSessions(): List<Session>

    suspend fun deleteSessionByDate(date: String)

}