package kaczmarek.moneycalculator.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import kaczmarek.moneycalculator.di.DIManager

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */

fun getString(resId: Int, vararg args: Any): String {
    return DIManager.appComponent.context.getString(resId, *args)
}

fun Context.dpToPx(dp : Int): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)
}

fun View.dpToPxInt(dp : Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.context.resources.displayMetrics).toInt()
}

inline val View.visible: View
    get() = apply { visibility = View.VISIBLE }

inline val View.gone: View
    get() = apply { visibility = View.GONE }
