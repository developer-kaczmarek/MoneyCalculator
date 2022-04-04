package kaczmarek.moneycalculator.message.ui

import kaczmarek.moneycalculator.core.ui.message.MessageData

interface MessageComponent {

    val visibleMessageData: MessageData?

    fun onActionClick()
}