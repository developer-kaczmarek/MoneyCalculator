package com.kaczmarek.moneycalculator.ui.main.repositories

import com.kaczmarek.moneycalculator.di.services.database.models.Session

interface IMainRepository {
    suspend fun getAll(): List<Session>
    fun getHistoryStoragePeriod(): Int
    suspend fun deleteSession(session: Session)
}