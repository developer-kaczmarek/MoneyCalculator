package com.kaczmarek.moneycalculator.ui.settings.interactors

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.settings.repositories.ISettingsRepository

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
class SettingsInteractor(private val repository: ISettingsRepository) {

    suspend fun getAllBanknotes() = repository.getAllBanknotes()

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
}