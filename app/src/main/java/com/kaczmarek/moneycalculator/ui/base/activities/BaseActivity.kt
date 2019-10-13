package com.kaczmarek.moneycalculator.ui.base.activities

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.views.BaseView

import com.ub.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.component_toolbar.*
import moxy.MvpAppCompatActivity

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    private var alertDialog: AlertDialog? = null
    private var toolbar: Toolbar? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        toolbar = findViewById(R.id.toolbar)
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