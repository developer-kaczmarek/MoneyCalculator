package kaczmarek.moneycalculator.ui.calculator

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.BanknoteCard
import kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
import moxy.presenterScope
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

class CalculatorPresenter : PresenterBase<CalculatorView>() {
    @Inject
    lateinit var interactor: InteractorCalculator
    val banknotes = arrayListOf<Banknote>()
    val components = arrayListOf<BanknoteCard>()
    var totalAmount: Double = 0.0

    init {
        DIManager.getCalculatorSubcomponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeCalculatorSubcomponent()
    }

    /**
     * Метод возвращающий флаг необходимо ли не гасить экран при осуществлении вычислений
     */
    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    /**
     * Метод возвращающий флаг необходимо ли не гасить экран при осуществлении вычислений
     */
    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()

    /**
     * Метод для подсчета и обновления итоговой суммы
     */
    fun updateTotalAmount() {
        totalAmount = 0.0
        banknotes.forEach {
            totalAmount += it.amount
        }
        viewState.updateTotalAmount()
    }

    /**
     * Метод для получения списка банкнот
     */
    fun getBanknotes() {
        presenterScope.launch {
            try {
                banknotes.clear()
                banknotes.addAll(interactor.getAll().filter { it.isShow })
                getCalculatorItems()
            } catch (e: Exception) {
                viewState.showMessage(
                    getString(
                        R.string.common_load_error,
                        e.toString()
                    )
                )
            }
        }
    }

    /**
     * Метод для сохранения текущей вычислительной сессии.
     * В случае если итоговая сумма ровна нулю сессия не будет сохранена
     */
    fun saveSession() {
        presenterScope.launch {
            try {
                if (totalAmount == 0.0) {
                    viewState.showMessage(getString(R.string.fragment_calculator_empty_total_amount_error))
                } else {
                    val completedBanknotes = arrayListOf<Banknote>()
                    completedBanknotes.addAll(banknotes.filter { it.count != 0 && it.isShow })
                    interactor.saveSession(totalAmount, completedBanknotes)
                    viewState.showMessage(getString(R.string.fragment_calculator_save_successful))
                }
            } catch (e: Exception) {
                viewState.showMessage(getString(R.string.common_save_error, e.toString()))
            }
        }
    }

    fun setCalculatorItems() {
        interactor.setCalculatorItems(banknotes)
    }

    private fun getCalculatorItems() {
        val calculatorItems = interactor.getCalculatorItems()
        if (isIdenticalLists(calculatorItems)) {
            calculatorItems.forEachIndexed { index, banknote ->
                if (banknotes[index].id == banknote.id) {
                    banknotes[index].count = banknote.count
                    banknotes[index].amount = banknote.amount
                }
            }
        }
        updateTotalAmount()
        viewState.addBanknoteCard()
    }

    private fun isIdenticalLists(calculatorItems: List<Banknote>): Boolean {
        var isIdenticalList = true
        if (calculatorItems.size != banknotes.size) {
            isIdenticalList = false
        }
        if (isIdenticalList) {
            calculatorItems.forEachIndexed { index, banknote ->
                if (banknotes[index].id != banknote.id) {
                    isIdenticalList = false
                }
            }
        }
        return isIdenticalList
    }

    /**
     * Метод для получением количества компонентов с которыми пользователь знаком
     */
    fun howMuchKnowComponents() = interactor.getCountMeetComponents()

    /**
     * Метод для обновления значения количества компонентов с которыми прошло первое знакомство
     */
    fun updateCountMeetComponent(countMeetComponent: Int) {
        interactor.updateCountMeetComponent(countMeetComponent)
    }

    /**
     * Метод возращающий флаг является итоговая сумма целым числом без чисел после запятой
     */
    fun isTotalAmountInteger(): Boolean = totalAmount - totalAmount.toInt() == 0.0
}