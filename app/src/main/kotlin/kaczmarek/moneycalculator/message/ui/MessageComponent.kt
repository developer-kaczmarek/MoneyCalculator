package kaczmarek.moneycalculator.message.ui

import kaczmarek.moneycalculator.core.ui.message.MessageData
import kaczmarek.moneycalculator.core.ui.widgets.dialog.DialogResult
import me.aartikov.sesame.dialog.DialogControl

interface MessageComponent {

    val dialogControl: DialogControl<MessageData, DialogResult>
}