package kaczmarek.moneycalculator.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kaczmarek.moneycalculator.R
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import me.aartikov.sesame.localizedstring.LocalizedString
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days

@Composable
fun LocalizedString.resolve(): String {
    LocalConfiguration.current
    return resolve(LocalContext.current).toString()
}

fun String.getFormattedDateHeader(): LocalizedString {
    val formatter = SimpleDateFormat(Patterns.DD_MM_YYYY, Locale.getDefault())
    val today = Date.from(Clock.System.now().toJavaInstant())
    val yesterday = Date.from(Clock.System.now().minus(1.days).toJavaInstant())
    return when (this) {
        formatter.format(today) -> LocalizedString.resource(R.string.sessions_today_sessions)
        formatter.format(yesterday) -> LocalizedString.resource(R.string.sessions_yesterday_sessions)
        else -> LocalizedString.raw(this)
    }
}