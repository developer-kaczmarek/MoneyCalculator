package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class InteractorHistory(private val repository: IRepositoryHistory) {

    fun getAll() = repository.getAll()

    fun deleteSession(session: Session) {
        repository.deleteSession(session)
    }

}