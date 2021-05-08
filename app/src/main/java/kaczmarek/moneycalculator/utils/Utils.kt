package kaczmarek.moneycalculator.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}