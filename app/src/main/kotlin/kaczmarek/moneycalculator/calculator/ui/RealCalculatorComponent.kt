package kaczmarek.moneycalculator.calculator.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import kaczmarek.moneycalculator.calculator.domain.GetCalculatingSessionInteractor
import kaczmarek.moneycalculator.core.ui.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.ui.error_handling.safeLaunch
import kaczmarek.moneycalculator.core.ui.message.MessageData
import kaczmarek.moneycalculator.core.ui.message.MessageService
import kaczmarek.moneycalculator.core.ui.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.ui.utils.toComposeState
import kaczmarek.moneycalculator.core.ui.utils.toFormattedAmount
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.dataOrNull
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh
import me.aartikov.sesame.localizedstring.LocalizedString

class RealCalculatorComponent(
    componentContext: ComponentContext,
    private val errorHandler: ErrorHandler,
    private val messageService: MessageService,
    getCalculatingSessionInteractor: GetCalculatingSessionInteractor
) : ComponentContext by componentContext, CalculatorComponent {

    private val coroutineScope = componentCoroutineScope()

    override var selectedBanknoteIndex: Int by mutableStateOf(0)
        private set

    private val calculatorLoading = OrdinaryLoading(
        coroutineScope,
        load = { getCalculatingSessionInteractor.execute() }
    )

    private val calculatorState by calculatorLoading.stateFlow.toComposeState(coroutineScope)

    override val calculatingSessionViewState by derivedStateOf {
        calculatorState.mapData { it.toViewData(selectedBanknoteIndex) }
    }

    init {
        lifecycle.doOnStart {
            calculatorLoading.refresh()
        }
    }

    override fun onDigitClick(digit: String) {
        coroutineScope.safeLaunch(errorHandler) {
            calculatorLoading.mutateData { calculatingSession ->
                calculatingSession.copy(
                    banknotes = calculatingSession.banknotes.toMutableList().also {
                        it.forEachIndexed { index, banknote ->
                            when {
                                index == selectedBanknoteIndex && banknote.count == "0" -> {
                                    it[index] = banknote.copy(
                                        count = digit,
                                        amount = (banknote.denomination * digit.toInt()).toFormattedAmount()
                                    )
                                }
                                index == selectedBanknoteIndex && banknote.count.count() < 3 -> {
                                    val digits = banknote.count + digit
                                    it[index] = banknote.copy(
                                        count = digits,
                                        amount = (banknote.denomination * digits.toInt()).toFormattedAmount()
                                    )
                                }
                            }
                        }
                    }.toList()
                )
            }
        }
    }

    override fun onBackspaceClick() {
        coroutineScope.safeLaunch(errorHandler) {
            calculatorLoading.mutateData { calculatingSession ->
                calculatingSession.copy(
                    banknotes = calculatingSession.banknotes.toMutableList().also {
                        it.forEachIndexed { index, banknote ->
                            when {
                                index == selectedBanknoteIndex && banknote.count.count() > 1 -> {
                                    val digits = banknote.count.dropLast(1)
                                    it[index] = banknote.copy(
                                        count = digits,
                                        amount = (banknote.denomination * digits.toInt()).toFormattedAmount()
                                    )
                                }
                                index == selectedBanknoteIndex && banknote.count.count() == 1 -> {
                                    it[index] = banknote.copy(count = "0", amount = "0")
                                }
                            }
                        }
                    }.toList()
                )
            }
        }
    }

    override fun onBanknoteCardClick(banknote: DetailedBanknoteViewData) {
        val index = calculatingSessionViewState.dataOrNull?.banknotes?.indexOf(banknote) ?: 0
        selectedBanknoteIndex = index
    }

    override fun onForwardClick() {
        if (selectedBanknoteIndex == 0) return
        selectedBanknoteIndex -= 1
    }

    override fun onNextClick() {
        if (selectedBanknoteIndex == calculatingSessionViewState.dataOrNull?.banknotes?.lastIndex) return
        selectedBanknoteIndex += 1
    }

    override fun onSaveClick() {
        coroutineScope.safeLaunch(errorHandler) {
            messageService.showMessage(MessageData(text = LocalizedString.raw("onSaveClick")))
        }
    }

    override fun onCountingDetailsClick() {
        coroutineScope.safeLaunch(errorHandler) {
            messageService.showMessage(MessageData(text = LocalizedString.raw("onCountingDetailsClick")))
        }
    }

    override fun onBackspaceLongClick() {
        coroutineScope.safeLaunch(errorHandler) {
            calculatorLoading.refresh()
            selectedBanknoteIndex = 0
        }
    }
}