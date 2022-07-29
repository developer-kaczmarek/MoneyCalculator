package kaczmarek.moneycalculator.core.message.domain

import me.aartikov.sesame.localizedstring.LocalizedString

data class MessageData(
    val text: LocalizedString,
    val timerVisible: Boolean = false,
    val buttonLabel: LocalizedString? = null,
    val onButtonClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)