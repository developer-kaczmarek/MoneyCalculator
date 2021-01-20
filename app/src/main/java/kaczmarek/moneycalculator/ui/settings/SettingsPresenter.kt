package kaczmarek.moneycalculator.ui.settings

import android.widget.CheckBox
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
class SettingsPresenter : PresenterBase<SettingsView>() {
    @Inject
    lateinit var interactor: InteractorSettings
    val banknotes = arrayListOf<Banknote>()
    //val components = arrayListOf<CheckBox>()

    init {
        DIManager.getSettingsSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeSettingsSubcomponent()
    }

    fun getAllBanknotes() {
        presenterScope.launch {
            try {
                banknotes.clear()
                //components.clear()
                banknotes.addAll(interactor.getAllBanknotes())
                viewState.setVisibilityBanknotes(banknotes.map {
                    SettingBanknoteItem(it.id, it.name, it.isShow)
                })
            } catch (e: Exception) {
                viewState.showMessage(getString(R.string.common_load_error, e.toString()))
            } finally {
                viewState.showContent()
            }
        }
    }

    /**
     * Метод для получения флага указывающего все ли банкноты на калькуляторе будут невидимы
     */
    fun isAllBanknotesInvisible(): Boolean = banknotes.none { it.isShow }

    /**
     * Метод возращающий выбраный период сохранения истории вычислительных сессий
     */
    fun getHistoryStoragePeriod() = interactor.getHistoryStoragePeriod()

    /**
     * Метод возращающий выбраный тип отображения клавиатуры на калькуляторе
     */
    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    /**
     * Метод возращающий флаг необходимо ли экран держать всегда включеным
     */
    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()

    /**
     * Метод возращающий количество компонентов с которыми познакомился
     * пользователь при первом использовании
     */
    fun getCountMeetComponents() = interactor.getCountMeetComponents()

    /**
     * Метод обновления количества компонентов с которыми познакомился
     * пользователь при первом использовании
     * В случае успешного знакомства со всеми компонентами
     * произойдет переход на калькулятор
     */
    fun updateCountMeetComponent(countMeetComponent: Int) {
        interactor.updateCountMeetComponent(countMeetComponent)
        viewState.returnToCalculator()
    }

    /**
     * Метод для сохранения всех настроек
     */
    fun saveAllSettings(
        stateStoragePeriod: Int,
        stateKeyboardLayout: Int,
        stateAlwaysOnDisplay: Boolean
    ) {
        presenterScope.launch {
            try {
                interactor.saveVisibilityBanknotes(banknotes)
                interactor.saveHistoryStoragePeriod(stateStoragePeriod)
                interactor.saveKeyboardLayout(stateKeyboardLayout)
                interactor.saveAlwaysOnDisplay(stateAlwaysOnDisplay)
                viewState.showMessage(getString(R.string.fragment_settings_save_successful))
            } catch (e: Exception) {
                viewState.showMessage(getString(R.string.common_save_error, e.toString()))
            }
        }
    }
}