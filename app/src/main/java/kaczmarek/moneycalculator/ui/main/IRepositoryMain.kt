package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.di.services.database.models.Session

interface IRepositoryMain {
    suspend fun getAll(): List<Session>
    fun getHistoryStoragePeriod(): Int
    suspend fun deleteSession(session: Session)
}