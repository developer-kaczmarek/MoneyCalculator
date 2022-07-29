package kaczmarek.moneycalculator.calculator.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.calculator.domain.GetCalculatingSessionInteractor
import kaczmarek.moneycalculator.core.banknote.domain.IsBanknotesVisibilityChangedInteractor
import kaczmarek.moneycalculator.core.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.error_handling.safeLaunch
import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.utils.*
import kaczmarek.moneycalculator.sessions.domain.SaveSessionInteractor
import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId
import kaczmarek.moneycalculator.settings.domain.IsSettingsChangedInteractor
import me.aartikov.sesame.loading.simple.*
import me.aartikov.sesame.localizedstring.LocalizedString
import java.util.*

class RealCalculatorComponent(
    componentContext: ComponentContext,
    private val onOutput: (CalculatorComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    private val messageService: MessageService,
    getCalculatingSessionInteractor: GetCalculatingSessionInteractor,
    private val saveSessionInteractor: SaveSessionInteractor,
    private val isBanknotesVisibilityChangedInteractor: IsBanknotesVisibilityChangedInteractor,
    private val isSettingsChangedInteractor: IsSettingsChangedInteractor
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

    private var firstBackTime = 0L

    init {
        backPressedHandler.register(::onBackPressed)

        lifecycle.doOnCreate {
            calculatorLoading.handleErrors(coroutineScope, errorHandler)
        }
        lifecycle.doOnStart {
            val refresh =
                isBanknotesVisibilityChangedInteractor.execute() || isSettingsChangedInteractor.execute()

            if (refresh || calculatorState is Loading.State.Empty) {
                calculatorLoading.refresh()
                selectedBanknoteIndex = 0
            }
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
            calculatorLoading.dataOrNull?.let { currentSession ->
                val totalAmount = currentSession.banknotes.sumOf { item ->
                    item.amount.filterNot { it.isWhitespace() }.toDouble()
                }

                if (totalAmount == 0.0) {
                    messageService.showMessage(
                        MessageData(
                            text = LocalizedString.resource(R.string.calculator_empty_total_amount_error)
                        )
                    )
                } else {
                    saveSessionInteractor.execute(
                        totalAmount,
                        currentSession.banknotes.filter { it.count.toInt() != 0 }
                    )
                    messageService.showMessage(
                        MessageData(text = LocalizedString.resource(R.string.calculator_save_successful))
                    )
                }
            }
        }
    }

    override fun onCountingDetailsClick() {
        calculatorLoading.dataOrNull?.let { currentSession ->
            val totalAmount = currentSession.banknotes.sumOf { item ->
                item.amount.filterNot { it.isWhitespace() }.toDouble()
            }

            if (totalAmount == 0.0) {
                messageService.showMessage(
                    MessageData(
                        text = LocalizedString.resource(R.string.calculator_open_details_error)
                    )
                )
            } else {
                onOutput(
                    CalculatorComponent.Output.DetailedSessionRequested(
                        Session(
                            id = SessionId(UUID.randomUUID().toString()),
                            date = getFormattedCurrentDate(),
                            time = getFormattedCurrentTime(),
                            totalAmount = totalAmount,
                            totalCount = currentSession.banknotes.sumOf { it.count.toInt() },
                            banknotes = currentSession.banknotes.filter { it.count.toInt() != 0 }
                        )
                    )
                )
            }
        }
    }

    override fun onBackspaceLongClick() {
        coroutineScope.safeLaunch(errorHandler) {
            calculatorLoading.refresh()
            selectedBanknoteIndex = 0
            messageService.showMessage(
                MessageData(text = LocalizedString.resource(R.string.calculator_all_delete_values))
            )
        }
    }

    private fun onBackPressed(): Boolean {
        return if (System.currentTimeMillis() - firstBackTime < 4000L && firstBackTime != 0L) {
            false
        } else {
            firstBackTime = System.currentTimeMillis()
            messageService.showMessage(
                MessageData(text = LocalizedString.resource(R.string.calculator_exit_description))
            )
            true
        }
    }
}