package com.kaczmarek.moneycalculator.ui.main.interactors

import com.kaczmarek.moneycalculator.di.services.database.models.Session
import com.kaczmarek.moneycalculator.ui.main.repositories.IMainRepository

class MainInteractor(private val repository: IMainRepository) {

    suspend fun getAll() = repository.getAll()

    fun getHistoryStoragePeriod() = repository.getHistoryStoragePeriod()

    suspend fun deleteSession(session: Session) {
        repository.deleteSession(session)
    }
}