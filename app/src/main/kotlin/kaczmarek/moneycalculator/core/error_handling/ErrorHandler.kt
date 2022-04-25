package kaczmarek.moneycalculator.core.error_handling

import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.message.domain.MessageData

class ErrorHandler(private val messageService: MessageService) {

    fun handleError(throwable: Throwable) {
        println("throwable = $throwable")
        messageService.showMessage(MessageData(text = throwable.errorMessage))
    }
}