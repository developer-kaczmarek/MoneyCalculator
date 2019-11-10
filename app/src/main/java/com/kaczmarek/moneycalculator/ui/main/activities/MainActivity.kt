package com.kaczmarek.moneycalculator.ui.main.activities

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.activities.BaseActivity
import com.kaczmarek.moneycalculator.ui.calculator.fragmens.CalculatorFragment
import com.kaczmarek.moneycalculator.ui.history.fragmens.HistoryFragment
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.ui.main.presenters.MainPresenter
import com.kaczmarek.moneycalculator.ui.main.views.MainView
import com.kaczmarek.moneycalculator.ui.settings.fragmens.SettingsFragment
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter

class MainActivity : BaseActivity(), MainView,
    BackStackChangeListener, ExternalNavigation {

    @InjectPresenter
    lateinit var presenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> {
                    presenter.addFragmentToStack(R.id.item_calculator)
                    attachFragment(fl_main_container.id, CalculatorFragment(), CalculatorFragment.TAG, true)
                    true
                }
                R.id.item_history -> {
                    presenter.addFragmentToStack(R.id.item_history)
                    attachFragment(fl_main_container.id, HistoryFragment(), HistoryFragment.TAG, true)
                    true
                }
                R.id.item_settings -> {
                    presenter.addFragmentToStack(R.id.item_settings)
                    attachFragment(fl_main_container.id, SettingsFragment(), SettingsFragment.TAG, true)

                    true
                }
                else -> false
            }
        }
    }

    override fun onNavigateTo(tabId: Int) {
        bnv_main.selectedItemId = tabId
    }


    override fun onFirstOpen() {
        bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
        presenter.addFragmentToStack(R.id.item_calculator)
        attachFragment(fl_main_container.id, CalculatorFragment(), CalculatorFragment.TAG)
    }

    override fun onBackStackChange(fragment: Fragment) {
        when (fragment) {
            is CalculatorFragment -> bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
            is HistoryFragment -> bnv_main.menu.findItem(R.id.item_history).isChecked = true
            is SettingsFragment -> bnv_main.menu.findItem(R.id.item_settings).isChecked = true
        }
    }

    private fun attachFragment(@IdRes containerId: Int, fragmentInstance: Fragment, tag: String?, isAddToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = fragmentInstance
            fragmentTransaction.add(containerId, fragment, tag)
        } else {
            fragmentTransaction.attach(fragment)
        }

        val curFrag = supportFragmentManager.primaryNavigationFragment
        if (fragment != curFrag) {
            if (curFrag != null) {
                fragmentTransaction.detach(curFrag)
            }

            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }

            fragmentTransaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()
        }
    }
}
