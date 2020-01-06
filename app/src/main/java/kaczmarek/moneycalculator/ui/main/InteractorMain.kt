package kaczmarek.moneycalculator.ui.main

import kaczmarek.moneycalculator.di.services.database.models.Session

class InteractorMain(private val repository: IRepositoryMain) {

    fun getAll() = repository.getAll()

    fun getHistoryStoragePeriod() = repository.getHistoryStoragePeriod()

    fun deleteSession(session: Session) {
        repository.deleteSession(session)
    }
}