package com.kaczmarek.moneycalculator.ui.calculator

import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.base.PresenterBase
import com.kaczmarek.moneycalculator.utils.BanknoteCard
import com.kaczmarek.moneycalculator.utils.getString
import kotlinx.coroutines.launch
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
    var totalAmount = 0F

    init {
        DIManager.getCalculatorSubcomponent().inject(this)
    }

    fun getKeyboardLayout() = interactor.getKeyboardLayout()

    override fun onDestroy() {
        super.onDestroy()
        DIManager.removeCalculatorSubcomponent()
    }

    fun updateTotalAmount() {
        totalAmount = 0F
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
        launch {
            try {
                if (totalAmount == 0F) {
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
    fun updateCountMeetComponent(countMeetComponent: Int) = interactor.updateCountMeetComponent(countMeetComponent)
}