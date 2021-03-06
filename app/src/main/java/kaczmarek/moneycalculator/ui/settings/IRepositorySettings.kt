package kaczmarek.moneycalculator.ui.settings

import kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 13.10.2019.
 */
interface IRepositorySettings {
    fun getAllBanknotes(): List<Banknote>
    fun saveHistoryStoragePeriod(setting: Int)
    fun saveKeyboardLayout(setting: Int)
    fun saveAlwaysOnDisplay(isSelected: Boolean)
    fun updateBanknote(banknote: Banknote)
    fun getHistoryStoragePeriod(): Int
    fun getKeyboardLayout(): Int
    fun isAlwaysOnDisplay(): Boolean
    fun getCountMeetComponents(): Int
    fun updateCountMeetComponent(countMeetComponent: Int)
}
