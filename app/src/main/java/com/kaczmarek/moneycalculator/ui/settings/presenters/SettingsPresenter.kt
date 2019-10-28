package com.kaczmarek.moneycalculator.ui.settings.presenters

import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.base.presenters.BasePresenter
import com.kaczmarek.moneycalculator.ui.settings.interactors.SettingsInteractor
import com.kaczmarek.moneycalculator.ui.settings.views.SettingsView
import com.kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>() {
    @Inject
    lateinit var interactor: SettingsInteractor
    val banknotes = arrayListOf<Banknote>()

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

    fun saveVisibilityBanknotes(){
        interactor.saveVisibilityBanknotes(banknotes)
    }

    fun getHistoryStoragePeriod() = interactor.getHistoryStoragePeriod()

    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()

}