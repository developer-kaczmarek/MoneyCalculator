package kaczmarek.moneycalculator.ui.calculator


import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.usecase.GetBanknotesListUseCase
import kaczmarek.moneycalculator.domain.session.usecase.SaveSessionUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetAlwaysBacklightOnUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetCountMeetComponentUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetKeyboardLayoutUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.UpdateCountMeetComponentUseCase
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.*
import kotlinx.coroutines.launch
import moxy.presenterScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

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
    lateinit var getBanknotesListUseCase: GetBanknotesListUseCase

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
    fun getKeyboardLayout() = getKeyboardLayoutUseCase.invoke()

    /**
     * Данный метод возвращает флаг необходимости поддержки экрана постоянно включенным
     */
    fun isAlwaysBacklightOn() = getAlwaysBacklightOnUseCase.invoke()

    /**
     * Метод для получением количества компонентов с которыми пользователь знаком
     */
    fun getCountKnownComponents() = getCountMeetComponentUseCase.invoke()

    /**
     * Метод для получением количества компонентов с которыми пользователь знаком
     */
    fun updateCountMeetComponent(count: Int) {
        updateCountMeetComponentUseCase.invoke(count)
    }

    /**
     * Метод для получением списка видимых банктнот
     */
    fun getVisibleBanknotes() {
        presenterScope.launch {
            try {
                banknotes.clear()
                banknotes.addAll(getBanknotesListUseCase.invoke().filter { it.isShow })
                viewState.addBanknoteCard()
                updateTotalAmount()
            } catch (e: Exception) {
                logError(TAG, e.toString())
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
            viewState.setTotalAmount(totalAmount.getFormattedAmount())
        } catch (e: Exception) {
            logError(TAG, e.toString())
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
                    saveSessionUseCase.invoke(
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date),
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(date),
                        totalAmount,
                        banknotes.filter { it.count != 0 && it.isShow }
                    )
                    viewState.showMessage(getString(R.string.fragment_calculator_save_successful))
                }
            } catch (e: Exception) {
                logError(TAG, e.toString())
                viewState.showMessage(getString(R.string.common_save_error, e.toString()))
            }
        }
    }



    fun updateCountAndAmountBanknote(position: Int, count: Int, amount: Float) {
        if (position != -1) {
            banknotes[position] = banknotes[position].copy(
                count = count,
                amount = amount
            )
        }
    }

    companion object {
        const val TAG = "CalculatorPresenter"
    }

}