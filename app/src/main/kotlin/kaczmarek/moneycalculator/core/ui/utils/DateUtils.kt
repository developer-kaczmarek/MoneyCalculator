package kaczmarek.moneycalculator.core.ui.utils

import kaczmarek.moneycalculator.core.ui.utils.Patterns.DD_MM_YYYY
import kaczmarek.moneycalculator.core.ui.utils.Patterns.HH_MM
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration

object Patterns {
    const val DD_MM_YYYY = "dd/MM/yyyy"
    const val HH_MM = "HH:mm"
}

fun getFormattedCurrentTime(): String {
    return SimpleDateFormat(HH_MM, Locale.getDefault()).format(Date())
}

fun getFormattedCurrentDate(): String {
    return SimpleDateFormat(DD_MM_YYYY, Locale.getDefault()).format(Date())
}

fun getFormattedDateAfterDaysSubtracted(days: Int): String {
    val date = Date.from(Clock.System.now().minus(Duration.Companion.days(days)).toJavaInstant())
    return SimpleDateFormat(DD_MM_YYYY, Locale.getDefault()).format(date)
}