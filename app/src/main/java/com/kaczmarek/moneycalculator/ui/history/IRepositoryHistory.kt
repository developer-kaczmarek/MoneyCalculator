package com.kaczmarek.moneycalculator.ui.history

import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
interface IRepositoryHistory {
    suspend fun getAll(): List<Session>
}
