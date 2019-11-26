package com.kaczmarek.moneycalculator.utils

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.DIManager
import com.ub.utils.dpToPx

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
fun FragmentActivity.toast(
    message: String,
    toastDuration: Int = Toast.LENGTH_SHORT,
    yOffset: Int = dpToPx(16).toInt()
) {
    val layout =
        layoutInflater.inflate(R.layout.component_toast, findViewById(R.id.ll_toast_container))

    val text = layout.findViewById(R.id.tv_toast_message) as TextView
    text.text = message.trim()

    Toast(this).apply {
        setGravity(Gravity.BOTTOM, 0, yOffset)
        duration = toastDuration
        view = layout
        show()
    }
}

fun getString(resId: Int, vararg args: Any): String {
    return DIManager.appComponent.context.getString(resId, *args)
}