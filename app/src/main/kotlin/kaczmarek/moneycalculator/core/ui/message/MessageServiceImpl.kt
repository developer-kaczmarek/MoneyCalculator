package kaczmarek.moneycalculator.core.ui.message

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class MessageServiceImpl : MessageService {

    private val channel = Channel<MessageData>(Channel.UNLIMITED)

    override val messageFlow = channel.receiveAsFlow()

    override fun showMessage(message: MessageData) {
        channel.trySend(message)
    }
}