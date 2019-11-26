package com.kaczmarek.moneycalculator.ui.settings

import android.widget.CheckBox
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.base.PresenterBase
import com.kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
@InjectViewState
class PresenterSettings : PresenterBase<ViewSettings>() {
    @Inject
    lateinit var interactor: InteractorSettings
    val banknotes = arrayListOf<Banknote>()
    val components = arrayListOf<CheckBox>()

    init {
        DIManager.getSettingsSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeSettingsSubcomponent()
    }

    fun getAllBanknotes() = launch {
        try {
            banknotes.clear()
            banknotes.addAll(interactor.getAllBanknotes())
            viewState.loadBanknotes()
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_load_error,
                    e.toString()
                )
            )
        }
    }

    fun saveHistoryStoragePeriod(@SettingsService.Companion.StoragePeriod setting: Int) {
        interactor.saveHistoryStoragePeriod(setting)
    }

    fun saveKeyboardLayout(@SettingsService.Companion.KeyboardLayout setting: Int) {
        interactor.saveKeyboardLayout(setting)
    }

    fun saveAlwaysOnDisplay(isSelected: Boolean) {
        interactor.saveAlwaysOnDisplay(isSelected)
    }

    fun areAllBanknotesInvisible(): Boolean {
        var allBanknotesInvisible = true
        banknotes.forEach {
            if (it.isShow) {
                allBanknotesInvisible = false
            }
        }
        return allBanknotesInvisible
    }

    fun saveVisibilityBanknotes() = launch {
        try {
            interactor.saveVisibilityBanknotes(banknotes)
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_save_error,
                    e.toString()
                )
            )
        }
    }

    fun getHistoryStoragePeriod() = interactor.getHistoryStoragePeriod()

    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()

}