package com.kaczmarek.moneycalculator.ui.history

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class InteractorHistory(private val repository: IRepositoryHistory) {

    suspend fun getAll() = repository.getAll()

}