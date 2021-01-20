package kaczmarek.moneycalculator.ui.base

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */

import android.content.Context
import android.widget.Toast
import androidx.annotation.LayoutRes
import kaczmarek.moneycalculator.utils.dpToPx
import kaczmarek.moneycalculator.utils.toast
import moxy.MvpAppCompatFragment

open class BaseFragment(@LayoutRes contentLayoutId: Int) : MvpAppCompatFragment(contentLayoutId) {

    var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        }
    }

    fun toast(message: String,
                        toastDuration: Int = Toast.LENGTH_SHORT,
                        yOffset: Int = requireContext().dpToPx(16).toInt()) {
        baseActivity?.toast(message, toastDuration, yOffset)
    }

}