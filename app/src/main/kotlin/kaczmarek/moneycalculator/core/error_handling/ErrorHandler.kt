package kaczmarek.moneycalculator.core.error_handling

import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.message.data.MessageService
import me.aartikov.sesame.localizedstring.LocalizedString

class ErrorHandler(private val messageService: MessageService) {

    fun handleError(throwable: Throwable) {
        messageService.showMessage(MessageData(text = LocalizedString.raw(throwable.message ?: "")))
    }
}