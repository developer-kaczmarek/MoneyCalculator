package kaczmarek.moneycalculator.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kaczmarek.moneycalculator.R
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

fun hideSoftKeyboard(context: Context) {
    try {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((context as Activity).currentFocus!!.windowToken, 0)
        context.currentFocus!!.clearFocus()
    } catch (e: NullPointerException) {
        Log.e("KeyBoard", e.toString())
    }
}

