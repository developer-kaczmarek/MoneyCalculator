package kaczmarek.moneycalculator.calculator.ui

import me.aartikov.sesame.loading.simple.Loading

interface CalculatorComponent {

    val calculatingSessionViewState: Loading.State<CalculatingSessionViewData>

    val selectedBanknoteIndex: Int

    fun onDigitClick(digit: String)

    fun onBackspaceClick()

    fun onBanknoteCardClick(banknote: DetailedBanknoteViewData)

    fun onForwardClick()

    fun onNextClick()

    fun onSaveClick()

    fun onCountingDetailsClick()

    fun onBackspaceLongClick()
}