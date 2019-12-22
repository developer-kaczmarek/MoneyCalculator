package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.di.services.database.models.Session

class InteractorMain(private val repository: IRepositoryMain) {

    suspend fun getAll() = repository.getAll()

    fun getHistoryStoragePeriod() = repository.getHistoryStoragePeriod()

    suspend fun deleteSession(session: Session) {
        repository.deleteSession(session)
    }
}