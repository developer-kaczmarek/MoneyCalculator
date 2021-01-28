package kaczmarek.moneycalculator.domain.session.port

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity


interface ISessionRepository {

    suspend fun deleteSessionsByDate(date: String)

    suspend fun getSessions(): List<SessionEntity>

    suspend fun saveCurrentSession(date: String, time: String, totalAmount: Double, banknotes: List<BanknoteEntity>)

    suspend fun deleteSession(sessionEntity: SessionEntity)

}