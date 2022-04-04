package kaczmarek.moneycalculator.core.ui.message

import androidx.annotation.DrawableRes
import me.aartikov.sesame.localizedstring.LocalizedString
import java.util.*

data class MessageData(
    val text: LocalizedString,
    @DrawableRes val iconRes: Int? = null,
    val actionTitle: LocalizedString? = null,
    val action: (() -> Unit)? = null
)