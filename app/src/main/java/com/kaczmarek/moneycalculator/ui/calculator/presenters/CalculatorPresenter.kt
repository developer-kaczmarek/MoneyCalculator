package com.kaczmarek.moneycalculator.ui.calculator.presenters

import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.ui.base.presenters.BasePresenter
import com.kaczmarek.moneycalculator.ui.calculator.views.CalculatorView
import com.kaczmarek.moneycalculator.utils.BanknoteCard
import kotlinx.android.synthetic.main.component_banknote_card.view.*
import moxy.InjectViewState

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@InjectViewState
class CalculatorPresenter : BasePresenter<CalculatorView>() {
    val banknote = arrayListOf<Banknote>()
    val components = arrayListOf<BanknoteCard>()
    var totalAmount = 0F

    fun getBanknoteList() {
        banknote.clear()
        banknote.add(Banknote(5000F, 0, 0f, "#ef5350", "#e57373", true))
        banknote.add(Banknote(100F, 0, 0f, "#FF7043", "#FF8A65", true))
        banknote.add(Banknote(50F, 0, 0f, "#66BB6A", "#81C784", true))
        banknote.add(Banknote(0.5F, 0, 0f, "#8D6E63", "#A1887F", true))
        updateTotalAmount()
        viewState.addBanknoteCard()
    }

    fun updateTotalAmount() {
        totalAmount = 0F
        banknote.forEach {
            totalAmount += it.amount
        }
        viewState.updateTotalAmount()
    }
}