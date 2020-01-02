package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
interface IRepositoryHistory {

    fun getAll(): List<Session>

}
