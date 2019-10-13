package com.kaczmarek.moneycalculator.ui.main.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.activities.BaseActivity
import com.kaczmarek.moneycalculator.ui.calculator.fragmens.CalculatorFragment
import com.kaczmarek.moneycalculator.ui.history.fragmens.HistoryFragment
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.ui.main.presenters.MainPresenter
import com.kaczmarek.moneycalculator.ui.main.views.MainView
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.kaczmarek.moneycalculator.utils.FragmentNavigation
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter

class MainActivity : BaseActivity(), MainView, FragmentNavigation,
    BackStackChangeListener, ExternalNavigation {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> openScreen(CalculatorFragment.TAG)
                R.id.item_history -> openScreen(HistoryFragment.TAG)
                else -> false
            }
        }
    }

    override fun onNavigateTo(tabId: Int) {
        bnv_main.selectedItemId = tabId
    }

    @Suppress("UNUSED_PARAMETER")
    fun openScreen(
        tag: String,
        extras: Bundle? = null,
        position: Int? = null,
        isAddToBackStack: Boolean = true,
        sharedElements: Map<String, View>? = null
    ): Boolean {
        return when (tag) {
            CalculatorFragment.TAG -> {
                presenter.addFragmentToStack(R.id.item_calculator)
                attachFragment(
                    fl_main_container.id,
                    CalculatorFragment(),
                    tag,
                    isAddToBackStack
                )
                true
            }
            HistoryFragment.TAG -> {
                presenter.addFragmentToStack(R.id.item_history)
                attachFragment(
                    fl_main_container.id,
                    HistoryFragment.newInstance(),
                    tag,
                    isAddToBackStack
                )
                true
            }
            else -> false
        }
    }

    override fun onFirstOpen() {
        openScreen(CalculatorFragment.TAG)
    }

    override fun onBackStackChange(fragment: Fragment) {
        when (fragment) {
            is CalculatorFragment -> {
                bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
            }
            is HistoryFragment -> {
                bnv_main.menu.findItem(R.id.item_history).isChecked = true
            }
        }
    }
}
