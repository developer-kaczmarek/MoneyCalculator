package kaczmarek.moneycalculator.ui.base

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.utils.hideSoftKeyboard
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
abstract class ActivityBase : MvpAppCompatActivity(),
    ViewBase {

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

    fun showBackArrowButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewBase : MvpView