package com.kaczmarek.moneycalculator.ui.history.repositories

import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
interface IHistoryRepository {
    suspend fun getAll(): List<Session>
    fun getHistoryStoragePeriod(): Int
    fun isAlwaysOnDisplay(): Boolean
    suspend fun deleteSession(session: Session)
}
