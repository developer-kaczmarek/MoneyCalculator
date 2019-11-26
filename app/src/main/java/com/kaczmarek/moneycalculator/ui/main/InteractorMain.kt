package com.kaczmarek.moneycalculator.ui.main

import com.kaczmarek.moneycalculator.di.services.database.models.Session

class InteractorMain(private val repository: IRepositoryMain) {

    suspend fun getAll() = repository.getAll()

    fun getHistoryStoragePeriod() = repository.getHistoryStoragePeriod()

    fun isAlwaysOnDisplay() = repository.isAlwaysOnDisplay()

    suspend fun deleteSession(session: Session) {
        repository.deleteSession(session)
    }
}