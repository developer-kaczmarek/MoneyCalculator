package kaczmarek.moneycalculator.ui.calculator

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.usecase.GetBanknotesListUseCase
import kaczmarek.moneycalculator.domain.banknote.usecase.GetTemporaryBanknotesListUseCase
import kaczmarek.moneycalculator.domain.banknote.usecase.SaveBanknotesTemporaryUseCase
import kaczmarek.moneycalculator.domain.session.usecase.SaveSessionUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetAlwaysBacklightOnUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetCountMeetComponentUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetKeyboardLayoutUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.UpdateCountMeetComponentUseCase
import kaczmarek.moneycalculator.ui.base.BasePresenter
import kaczmarek.moneycalculator.utils.*
import kotlinx.coroutines.launch
import moxy.presenterScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

class CalculatorPresenter : BasePresenter<CalculatorView>() {

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

    @Inject
    lateinit var saveBanknotesTemporaryUseCase: SaveBanknotesTemporaryUseCase

    @Inject
    lateinit var getTemporaryBanknotesListUseCase: GetTemporaryBanknotesListUseCase

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
                val visibleBanknotes = getBanknotesListUseCase.invoke().filter { it.isShow }
                val temporaryBanknotes = getTemporaryBanknotesListUseCase.invoke()
                logDebug(TAG, temporaryBanknotes.toString())
                banknotes.addAll(
                    if (temporaryBanknotes.isNotEmpty() &&
                        visibleBanknotes.isIdenticalLists(temporaryBanknotes)
                    ) {
                        temporaryBanknotes
                    } else {
                        visibleBanknotes
                    }
                )
                viewState.showBanknoteCards()
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

    /**
     * Метод для сохранения текущий значений банктнот
     * во времененное хранилище, которое живет пока работает приложение.
     * Это позволит сохранить значения, если не было изменений в настройках приложения
     */
    fun saveBanknotesFromCurrentSession() {
        try {
            saveBanknotesTemporaryUseCase.invoke(banknotes)
        } catch (e: Exception) {
            logError(TAG, e.toString())
        }
    }

    /**
     * Метод для обновления количества и суммы банктноты
     */
    fun updateCountAndAmountBanknote(position: Int, count: Int, amount: Float) {
        if (position != -1) {
            banknotes[position] = banknotes[position].copy(
                count = count,
                amount = amount
            )
        }
    }

    /**
     * Метод для сверки двух списков с банкнотами на идентичность по размеру и Id элементов
     */
    private fun List<BanknoteEntity>.isIdenticalLists(other: List<BanknoteEntity>): Boolean {
        var isIdentical = true
        if (other.size != this.size) {
            isIdentical = false
        } else {
            other.filterIndexed { index, otherBanknote ->
                this[index].id != otherBanknote.id
            }.firstOrNull()?.let {
                isIdentical = false
            }
        }
        return isIdentical
    }

    companion object {
        const val TAG = "CalculatorPresenter"
    }
}