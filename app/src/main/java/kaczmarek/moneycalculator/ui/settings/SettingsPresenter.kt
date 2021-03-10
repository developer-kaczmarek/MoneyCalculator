package kaczmarek.moneycalculator.ui.settings

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.banknote.usecase.GetBanknotesListUseCase
import kaczmarek.moneycalculator.domain.banknote.usecase.UpdateVisibilityBanknoteUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.*
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
class SettingsPresenter : PresenterBase<SettingsView>() {
    val banknotes = arrayListOf<SettingBanknoteItem>()

    @Inject
    lateinit var getHistoryStoragePeriodUseCase: GetHistoryStoragePeriodUseCase

    @Inject
    lateinit var getKeyboardLayoutUseCase: GetKeyboardLayoutUseCase

    @Inject
    lateinit var getAlwaysBacklightOnUseCase: GetAlwaysBacklightOnUseCase

    @Inject
    lateinit var getCountMeetComponentUseCase: GetCountMeetComponentUseCase

    @Inject
    lateinit var setHistoryStoragePeriodUseCase: SetHistoryStoragePeriodUseCase

    @Inject
    lateinit var setKeyboardLayoutUseCase: SetKeyboardLayoutUseCase

    @Inject
    lateinit var setAlwaysBacklightOnUseCase: SetAlwaysBacklightOnUseCase

    @Inject
    lateinit var updateCountMeetComponentUseCase: UpdateCountMeetComponentUseCase

    @Inject
    lateinit var getBanknotesListUseCase: GetBanknotesListUseCase

    @Inject
    lateinit var updateVisibilityBanknoteUseCase: UpdateVisibilityBanknoteUseCase


    init {
        DIManager.getSettingsSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeSettingsSubcomponent()
    }

    /**
     * Метод для получения списка всех банкнот для настройки видимости
     */
    fun getAllBanknotes() {
        presenterScope.launch {
            try {
                banknotes.clear()
                banknotes.addAll(getBanknotesListUseCase.invoke().map {
                    SettingBanknoteItem(it.id, it.name, it.isShow)
                })
                viewState.setVisibilityBanknotes(banknotes)
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
    fun getHistoryStoragePeriod(): Int = getHistoryStoragePeriodUseCase.invoke()

    /**
     * Метод возращающий выбраный тип отображения клавиатуры на калькуляторе
     */
    fun getKeyboardLayout(): Int = getKeyboardLayoutUseCase.invoke()

    /**
     * Метод возращающий флаг необходимо ли экран держать всегда включеным
     */
    fun isAlwaysOnDisplay(): Boolean = getAlwaysBacklightOnUseCase.invoke()

    /**
     * Метод возращающий количество компонентов с которыми познакомился
     * пользователь при первом использовании
     */
    fun getCountMeetComponents(): Int = getCountMeetComponentUseCase.invoke()

    /**
     * Метод обновления количества компонентов с которыми познакомился
     * пользователь при первом использовании
     * В случае успешного знакомства со всеми компонентами
     * произойдет переход на калькулятор
     */
    fun updateCountMeetComponent(countMeetComponent: Int) {
        updateCountMeetComponentUseCase.invoke(countMeetComponent)
        viewState.returnToCalculator()
    }

    /**
     * Метод для сохранения всех настроек
     */
    fun saveAllSettings(
        stateStoragePeriod: Int,
        stateKeyboardLayout: Int,
        isAlwaysBacklightOn: Boolean
    ) {
        presenterScope.launch {
            try {
                setAlwaysBacklightOnUseCase.invoke(isAlwaysBacklightOn)
                setHistoryStoragePeriodUseCase.invoke(stateStoragePeriod)
                setKeyboardLayoutUseCase.invoke(stateKeyboardLayout)
                banknotes.map { updateVisibilityBanknoteUseCase.invoke(it.id, it.isShow) }
                viewState.showMessage(getString(R.string.fragment_settings_save_successful))
            } catch (e: Exception) {
                viewState.showMessage(getString(R.string.common_save_error, e.toString()))
            }
        }
    }
}