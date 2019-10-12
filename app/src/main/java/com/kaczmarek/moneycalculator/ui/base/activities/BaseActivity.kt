package com.kaczmarek.moneycalculator.ui.base.activities

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.kaczmarek.moneycalculator.ui.base.views.BaseView

import com.ub.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.component_toolbar.*
import moxy.MvpAppCompatActivity

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    private var alertDialog: AlertDialog? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        toolbar?.let { setSupportActionBar(toolbar) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                hideSoftKeyboard(this)
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}