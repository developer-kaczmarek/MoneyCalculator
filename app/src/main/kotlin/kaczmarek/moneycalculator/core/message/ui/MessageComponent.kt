package kaczmarek.moneycalculator.core.message.ui

import kaczmarek.moneycalculator.core.message.domain.MessageData

interface MessageComponent {

    val visibleMessageData: MessageData?

    val timerData: TimerData?

    fun onActionClick()
}