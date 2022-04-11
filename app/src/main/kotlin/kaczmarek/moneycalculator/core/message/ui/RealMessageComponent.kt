package kaczmarek.moneycalculator.core.message.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.message.data.MessageService
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
        private const val ShowTime = 4000L
    }

    private val coroutineScope = componentCoroutineScope()

    override var visibleMessageData by mutableStateOf<MessageData?>(null)
        private set

    private var autoDismissJob: Job? = null

    init {
        lifecycle.doOnCreate(::collectMessages)
    }

    override fun onActionClick() {
        coroutineScope.launch {
            autoDismissJob?.cancel()
            visibleMessageData?.action?.invoke()
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

    private fun showMessage(messageData: MessageData) {
        autoDismissJob?.cancel()
        visibleMessageData = messageData
        autoDismissJob = coroutineScope.launch {
            delay(ShowTime)
            visibleMessageData = null
        }
    }
}