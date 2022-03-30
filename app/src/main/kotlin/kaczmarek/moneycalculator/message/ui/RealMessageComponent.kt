package kaczmarek.moneycalculator.message.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.core.ui.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.ui.message.MessageData
import kaczmarek.moneycalculator.core.ui.message.MessageService
import kaczmarek.moneycalculator.core.ui.widgets.dialog.DialogResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.aartikov.sesame.dialog.DialogControl

class RealMessageComponent(
    componentContext: ComponentContext,
    private val messageService: MessageService,
) : ComponentContext by componentContext, MessageComponent {

    private val coroutineScope = componentCoroutineScope()

    override val dialogControl = DialogControl<MessageData, DialogResult>()

    init {
        lifecycle.doOnCreate(::collectMessages)
    }

    private fun collectMessages() {
        coroutineScope.launch {
            messageService.messageFlow.collect { messageData ->
                dialogControl.show(messageData)
            }
        }
    }
}