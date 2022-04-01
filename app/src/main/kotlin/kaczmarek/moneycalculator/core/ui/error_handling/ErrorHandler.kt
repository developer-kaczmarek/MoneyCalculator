package kaczmarek.moneycalculator.core.ui.error_handling

import kaczmarek.moneycalculator.core.ui.message.MessageData
import kaczmarek.moneycalculator.core.ui.message.MessageService
import me.aartikov.sesame.localizedstring.LocalizedString

class ErrorHandler(private val messageService: MessageService) {

    fun handleError(throwable: Throwable) {
        messageService.showMessage(MessageData(text = LocalizedString.raw(throwable.message ?: "")))
    }
}