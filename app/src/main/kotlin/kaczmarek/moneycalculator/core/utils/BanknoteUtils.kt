package kaczmarek.moneycalculator.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.floor

fun Double.toFormattedAmount(): String {
    val separator = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = ' '
        decimalSeparator = '.'
    }

    val currentPattern = if (this.isInteger()) "#.##" else "0.00"
    val decimalFormat = DecimalFormat(currentPattern).apply {
        isGroupingUsed = true
        groupingSize = 3
        decimalFormatSymbols = separator
    }
    return decimalFormat.format(this)
}

fun Double.isInteger(): Boolean {
    return floor(this) == this
}