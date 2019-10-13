package com.kaczmarek.moneycalculator.ui.main.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.activities.BaseActivity
import com.kaczmarek.moneycalculator.ui.base.fragmens.BaseFragment
import com.kaczmarek.moneycalculator.ui.calculator.fragmens.CalculatorFragment
import com.kaczmarek.moneycalculator.ui.history.fragmens.HistoryFragment
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.ui.main.presenters.MainPresenter
import com.kaczmarek.moneycalculator.ui.main.views.MainView
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter

class MainActivity : BaseActivity(), MainView,
    BackStackChangeListener, ExternalNavigation {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    val calculator =  CalculatorFragment()
    val history =  HistoryFragment()
    var activeFragment:BaseFragment = calculator
    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(calculator).commit()
                    activeFragment = calculator
                    true
                }
                R.id.item_history -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(history).detach(history).attach(history).commit()
                    activeFragment = history

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
        fragmentManager.beginTransaction().add(R.id.fl_main_container,calculator, CalculatorFragment.TAG).commit()
        fragmentManager.beginTransaction().add(R.id.fl_main_container, history, HistoryFragment.TAG).hide(history).commit()
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
