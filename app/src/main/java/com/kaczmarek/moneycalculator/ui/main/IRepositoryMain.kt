package com.kaczmarek.moneycalculator.ui.main

import com.kaczmarek.moneycalculator.di.services.database.models.Session

interface IRepositoryMain {
    suspend fun getAll(): List<Session>
    fun getHistoryStoragePeriod(): Int
    fun isAlwaysOnDisplay(): Boolean
    suspend fun deleteSession(session: Session)
}