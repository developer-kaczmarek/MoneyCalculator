package kaczmarek.moneycalculator.ui.base

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */

import android.content.Context
import androidx.annotation.LayoutRes
import moxy.MvpAppCompatFragment

open class BaseFragment(@LayoutRes contentLayoutId: Int) : MvpAppCompatFragment(contentLayoutId) {

    var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        }
    }

    fun showMessage(message: String) {
        baseActivity?.showMessage(message = message)
    }

}