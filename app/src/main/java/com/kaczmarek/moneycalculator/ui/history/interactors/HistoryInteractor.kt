package com.kaczmarek.moneycalculator.ui.history.interactors

import com.kaczmarek.moneycalculator.ui.history.repositories.IHistoryRepository

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class HistoryInteractor(private val repository: IHistoryRepository) {

    suspend fun getAll() = repository.getAll()
}