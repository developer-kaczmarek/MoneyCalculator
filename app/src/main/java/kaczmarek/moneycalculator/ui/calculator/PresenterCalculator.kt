package kaczmarek.moneycalculator.ui.calculator

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kaczmarek.moneycalculator.di.services.database.models.Banknote
import kaczmarek.moneycalculator.ui.base.PresenterBase
import kaczmarek.moneycalculator.utils.BanknoteCard
import kaczmarek.moneycalculator.utils.getString
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@InjectViewState
class PresenterCalculator : PresenterBase<ViewCalculator>() {
    @Inject
    lateinit var interactor: InteractorCalculator
    val banknotes = arrayListOf<Banknote>()
    val components = arrayListOf<BanknoteCard>()
    var totalAmount: Double = 0.0

    init {
        DIManager.getCalculatorSubcomponent().inject(this)
    }

    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    fun isAlwaysOnDisplay() = interactor.isAlwaysOnDisplay()


    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeCalculatorSubcomponent()
    }

    fun updateTotalAmount() {
        totalAmount = 0.0
        banknotes.forEach {
            totalAmount += it.amount
        }
        viewState.updateTotalAmount()
    }

    fun getBanknotes() {
        banknotes.clear()
        val allBanknotes = interactor.getAll()
        allBanknotes.forEach {
            if (it.isShow) {
                banknotes.add(it)
            }
        }
        getCalculatorItems()
    }

    fun saveSession() {
        try {
            if (totalAmount == 0.0) {
                viewState.showMessage(getString(R.string.fragment_calculator_empty_total_amount_error))
            } else {
                val completedBanknotes = arrayListOf<Banknote>()
                banknotes.forEach {
                    if (it.count != 0 && it.isShow) {
                        completedBanknotes.add(it)
                    }
                }
                interactor.saveSession(totalAmount, completedBanknotes)
                viewState.showMessage(getString(R.string.fragment_calculator_save_successful))
            }
        } catch (e: Exception) {
            viewState.showMessage(
                getString(
                    R.string.common_save_error,
                    e.toString()
                )
            )
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

    fun howMuchKnowComponents() = interactor.getCountMeetComponents()
    fun updateCountMeetComponent(countMeetComponent: Int) =
        interactor.updateCountMeetComponent(countMeetComponent)
}