package kaczmarek.moneycalculator.core.ui.message

import kaczmarek.moneycalculator.core.ui.message.MessageData
import kotlinx.coroutines.flow.Flow

interface MessageService {

    val messageFlow: Flow<MessageData>

    fun showMessage(message: MessageData)
}