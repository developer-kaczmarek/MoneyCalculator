package com.kaczmarek.moneycalculator.ui.base

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.utils.dpToPx
import com.kaczmarek.moneycalculator.utils.toast
import moxy.MvpAppCompatFragment

open class FragmentBase : MvpAppCompatFragment() {

    var baseActivity: ActivityBase? = null

    private var toolbar: Toolbar? = null
    private var vToolbarShadow: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityBase) {
            baseActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        vToolbarShadow = view.findViewById(R.id.v_toolbar_shadow)

        toolbar?.let {
            baseActivity?.setSupportActionBar(toolbar)
        }
    }
    protected fun setTitle(@StringRes titleId: Int) {
        baseActivity?.setTitle(titleId)
    }

    protected fun toast(message: String,
                        toastDuration: Int = Toast.LENGTH_SHORT,
                        yOffset: Int = requireContext().dpToPx(16).toInt()) {
        baseActivity?.toast(message, toastDuration, yOffset)
    }

}