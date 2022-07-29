package kaczmarek.moneycalculator.core.message.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.utils.componentCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RealMessageComponent(
    componentContext: ComponentContext,
    private val messageService: MessageService,
) : ComponentContext by componentContext, MessageComponent {

    companion object {
        private const val TOTAL_TIME = 4000F
        private const val DELAY_VALUE = 4000L
    }

    private val coroutineScope = componentCoroutineScope()

    override var visibleMessageData by mutableStateOf<MessageData?>(null)
        private set

    private var autoDismissJob: Job? = null

    private var autoDismissTimerJob: Job? = null

    private var currentTime by mutableStateOf(TOTAL_TIME)

    override val timerData: TimerData? by derivedStateOf {
        if (visibleMessageData?.timerVisible == true) {
            TimerData(
                label = (currentTime.toInt() / 1000 + 1).toString(),
                progress = currentTime / TOTAL_TIME
            )
        } else {
            null
        }
    }

    init {
        lifecycle.doOnCreate(::collectMessages)
    }

    override fun onActionClick() {
        coroutineScope.launch {
            autoDismissTimerJob?.cancel()
            autoDismissJob?.cancel()
            visibleMessageData?.onButtonClick?.invoke()
            visibleMessageData = null
        }
    }

    private fun collectMessages() {
        coroutineScope.launch {
            messageService.messageFlow.collect { messageData ->
                showMessage(messageData)
            }
        }
    }

    private suspend fun calculateTick() {
        if (currentTime > 0) {
            delay(100L)
            currentTime -= 100L
            calculateTick()
        }
    }

    private fun showMessage(messageData: MessageData) {
        autoDismissTimerJob?.cancel()
        autoDismissJob?.cancel()

        currentTime = TOTAL_TIME
        visibleMessageData = messageData

        if (messageData.timerVisible) {
            autoDismissTimerJob = coroutineScope.launch {
                calculateTick()
            }
        }
        autoDismissJob = coroutineScope.launch {
            delay(DELAY_VALUE)
            visibleMessageData?.onDismiss?.invoke()
            visibleMessageData = null
        }
    }
}