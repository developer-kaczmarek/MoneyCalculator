package kaczmarek.moneycalculator.ui.settings

import android.widget.CheckBox
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
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

    fun getAllBanknotes() {
        try {
            val banknotesFromDatabase = interactor.getAllBanknotes()
            banknotes.clear()
            components.clear()
            banknotes.addAll(banknotesFromDatabase)
            viewState.loadBanknotes()
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_load_error,
                    e.toString()
                )
            )
        }
        finally {
            viewState.showContent()
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

    fun saveVisibilityBanknotes() {
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

    fun howMuchKnowComponents() = interactor.getCountMeetComponents()

    fun updateCountMeetComponent(countMeetComponent: Int) =
        interactor.updateCountMeetComponent(countMeetComponent)

}