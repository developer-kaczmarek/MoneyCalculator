package com.kaczmarek.moneycalculator.ui.calculator.presenters

import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.base.presenters.BasePresenter
import com.kaczmarek.moneycalculator.ui.calculator.interactors.CalculatorInteractor
import com.kaczmarek.moneycalculator.ui.calculator.views.CalculatorView
import com.kaczmarek.moneycalculator.utils.BanknoteCard
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@InjectViewState
class CalculatorPresenter : BasePresenter<CalculatorView>() {
    @Inject lateinit var interactor: CalculatorInteractor
    val banknotes = arrayListOf<Banknote>()
    val components = arrayListOf<BanknoteCard>()
    var totalAmount = 0F

    init {
        DIManager.getCalculatorSubcomponent().inject(this)
    }

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

    fun getBanknotes(){
        banknotes.clear()
        banknotes.addAll(interactor.getAll())
        updateTotalAmount()
        viewState.addBanknoteCard()
    }
}