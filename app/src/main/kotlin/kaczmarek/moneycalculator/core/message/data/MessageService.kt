package kaczmarek.moneycalculator.core.message.data

import kaczmarek.moneycalculator.core.message.domain.MessageData
import kotlinx.coroutines.flow.Flow

interface MessageService {

    val messageFlow: Flow<MessageData>

    fun showMessage(message: MessageData)
}