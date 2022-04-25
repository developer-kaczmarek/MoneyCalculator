package kaczmarek.moneycalculator.sessions.data

import kaczmarek.moneycalculator.core.banknote.data.BanknotesDao
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId

class SessionsStorageImpl(
    private val sessionsDao: SessionsDao,
    private val banknotesDao: BanknotesDao
) : SessionsStorage {

    override suspend fun getSessions(): List<Session> {
        return sessionsDao.getSessions().map { it.toSession() }
    }

    override suspend fun deleteSessionById(id: SessionId) {
        sessionsDao.deleteSessionById(id.value.toInt())
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
                banknotes = banknotes.map {
                    banknotesDao.getBanknoteById(it.id.value.toInt()).copy(
                        count = it.count.toInt(),
                        amount = it.amount.replace(" ", "").toDouble()
                    )
                }
            )
        )
    }

    override suspend fun deleteSessionsByDate(date: String) {
        sessionsDao.deleteSessionsByDate(date)
    }
}