package kaczmarek.moneycalculator.ui.history

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class InteractorHistory(private val repository: IRepositoryHistory) {

    fun getAll() = repository.getAll()

}