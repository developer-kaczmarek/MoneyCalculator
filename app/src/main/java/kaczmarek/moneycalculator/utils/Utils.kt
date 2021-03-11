package kaczmarek.moneycalculator.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.DIManager
import kotlin.math.floor

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */

fun getString(resId: Int, vararg args: Any): String {
    return DIManager.appComponent.context.getString(resId, *args)
}

fun Context.dpToPx(dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    )
}

inline val View.visible: View
    get() = apply { visibility = View.VISIBLE }

inline val View.gone: View
    get() = apply { visibility = View.GONE }

/**
 * Метод для определения целочисленным ли является число типа Double
 */
fun Double.isInteger(): Boolean {
    return this == floor(this) && this.isFinite()
}

/**
 * Метод для определения целочисленным ли является число типа Double
 */
fun Double.getFormattedAmount(): String {
    return if (this.isInteger()) {
        getString(R.string.common_ruble_format, this.toInt())
    } else {
        getString(R.string.common_ruble_float_format, this)
    }
}
