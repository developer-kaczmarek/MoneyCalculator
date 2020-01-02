package kaczmarek.moneycalculator.ui.settings

import kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class InteractorSettings(private val repository: IRepositorySettings) {

    fun getAllBanknotes() = repository.getAllBanknotes()

    fun saveHistoryStoragePeriod(setting: Int) {
        repository.saveHistoryStoragePeriod(setting)
    }

    fun saveKeyboardLayout(setting: Int) {
        repository.saveKeyboardLayout(setting)
    }

    fun saveAlwaysOnDisplay(isSelected: Boolean) {
        repository.saveAlwaysOnDisplay(isSelected)
    }

    fun saveVisibilityBanknotes(banknotes: List<Banknote>) {
        banknotes.forEach {
            repository.updateBanknote(it)
        }
    }

    fun getHistoryStoragePeriod() = repository.getHistoryStoragePeriod()

    fun getKeyboardLayout() = repository.getKeyboardLayout()

    fun isAlwaysOnDisplay() = repository.isAlwaysOnDisplay()

    fun getCountMeetComponents() = repository.getCountMeetComponents()

    fun updateCountMeetComponent(countMeetComponent: Int) =
        repository.updateCountMeetComponent(countMeetComponent)
}