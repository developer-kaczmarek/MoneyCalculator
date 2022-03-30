package kaczmarek.moneycalculator.core.ui.message

import androidx.annotation.DrawableRes
import me.aartikov.sesame.localizedstring.LocalizedString
import java.util.*

data class MessageData(
    val id: UUID = UUID.randomUUID(),
    val text: LocalizedString,
    @DrawableRes val iconRes: Int? = null
)