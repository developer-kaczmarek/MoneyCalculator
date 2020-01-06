package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.di.services.database.models.Session

interface IRepositoryMain {
    fun getAll(): List<Session>
    fun getHistoryStoragePeriod(): Int
    fun deleteSession(session: Session)
}