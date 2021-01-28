package kaczmarek.moneycalculator.ui.calculator


import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.usecase.GetBanknoteUseCase
import kaczmarek.moneycalculator.domain.session.usecase.SaveSessionUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetAlwaysBacklightOnUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetCountMeetComponentUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetKeyboardLayoutUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.UpdateCountMeetComponentUseCase
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.logDebug
import kotlinx.coroutines.launch
import moxy.presenterScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.floor

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

class CalculatorPresenter : PresenterBase<CalculatorView>() {

    var totalAmount: Double = 0.0
    val banknotes = arrayListOf<BanknoteEntity>()

    @Inject
    lateinit var getKeyboardLayoutUseCase: GetKeyboardLayoutUseCase

    @Inject
    lateinit var getAlwaysBacklightOnUseCase: GetAlwaysBacklightOnUseCase

    @Inject
    lateinit var getCountMeetComponentUseCase: GetCountMeetComponentUseCase

    @Inject
    lateinit var updateCountMeetComponentUseCase: UpdateCountMeetComponentUseCase

    @Inject
    lateinit var getBanknoteUseCase: GetBanknoteUseCase

    @Inject
    lateinit var saveSessionUseCase: SaveSessionUseCase

    init {
        DIManager.getCalculatorSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeCalculatorSubcomponent()
    }

    /**
     * Данный метод возвращает тип отображения клавиатуры
     */
    fun getKeyboardLayout() = getKeyboardLayoutUseCase.getType()

    /**
     * Данный метод возвращает флаг необходимости поддержки экрана постоянно включенным
     */
    fun isAlwaysBacklightOn() = getAlwaysBacklightOnUseCase.isAlwaysBacklightOn()

    /**
     * Метод для получением количества компонентов с которыми пользователь знаком
     */
    fun getCountKnownComponents() = getCountMeetComponentUseCase.getCount()

    /**
     * Метод для получением количества компонентов с которыми пользователь знаком
     */
    fun updateCountMeetComponent(count: Int) {
        updateCountMeetComponentUseCase.updateCount(count)
    }

    /**
     * Метод для получением списка видимых банктнот
     */
    fun getVisibleBanknotes() {
        presenterScope.launch {
            try {
                banknotes.clear()
                banknotes.addAll(getBanknoteUseCase.getList().filter { it.isShow })
                viewState.addBanknoteCard()
                updateTotalAmount()
            } catch (e: Exception) {
                logDebug(TAG, e.message)
                viewState.showMessage(getString(R.string.common_load_error, e.toString()))
            }
        }
    }

    /**
     * Метод обновления общей суммы всех банкнот
     */
    fun updateTotalAmount() {
        try {
            totalAmount = banknotes.map { it.amount.toDouble() }.sum()
            viewState.setTotalAmount(
                if (totalAmount.isInteger()) {
                    getString(R.string.common_ruble_format, totalAmount.toInt())
                } else {
                    getString(R.string.common_ruble_float_format, totalAmount)
                }
            )
        } catch (e: Exception) {
            logDebug(TAG, e.message)
        }
    }

    /**
     * Метод для сохранения текущий вычислительной сессия в БД
     */
    fun saveCurrentCalculatingSession() {
        presenterScope.launch {
            try {
                if (totalAmount == 0.0) {
                    viewState.showMessage(getString(R.string.fragment_calculator_empty_total_amount_error))
                } else {
                    val date = Date()
                    saveSessionUseCase.save(
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date),
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(date),
                        totalAmount,
                        banknotes.filter { it.count != 0 && it.isShow }
                    )
                    viewState.showMessage(getString(R.string.fragment_calculator_save_successful))
                }
            } catch (e: Exception) {
                logDebug(TAG, e.message)
                viewState.showMessage(getString(R.string.common_save_error, e.toString()))
            }
        }
    }

    /**
     * Метод для определения целочисленным ли является число типа Double
     */
    private fun Double.isInteger(): Boolean {
        return this == floor(this) && this.isFinite()
    }

    companion object {
        const val TAG = "CalculatorPresenter"
    }

}