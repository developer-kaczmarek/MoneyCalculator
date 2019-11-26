package com.kaczmarek.moneycalculator.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.ActivityBase
import com.kaczmarek.moneycalculator.ui.base.ViewBase
import com.kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import com.kaczmarek.moneycalculator.ui.history.FragmentHistory
import com.kaczmarek.moneycalculator.ui.settings.FragmentSettings
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.kaczmarek.moneycalculator.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

class MainActivity : ActivityBase(),
    ViewMain,
    BackStackChangeListenerMain, ExternalNavigation {

    @InjectPresenter
    lateinit var presenter: PresenterMain
    var currentFragment: Fragment? = null
    var timeFirstBack = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (presenter.isAlwaysOnDisplay()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        bnv_main.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> {
                    presenter.addFragmentToStack(R.id.item_calculator)
                    attachFragment(
                        fl_main_container.id,
                        CalculatorFragment(),
                        CalculatorFragment.TAG
                    )
                    true
                }
                R.id.item_history -> {
                    presenter.addFragmentToStack(R.id.item_history)
                    attachFragment(fl_main_container.id,
                        FragmentHistory(), FragmentHistory.TAG)
                    true
                }
                R.id.item_settings -> {
                    presenter.addFragmentToStack(R.id.item_settings)
                    attachFragment(fl_main_container.id,
                        FragmentSettings(), FragmentSettings.TAG)

                    true
                }
                else -> false
            }
        }
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    override fun onNavigateTo(tabId: Int) {
        bnv_main.selectedItemId = tabId
    }


    override fun onFirstOpen() {
        bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
        presenter.addFragmentToStack(R.id.item_calculator)
        attachFragment(fl_main_container.id,
            CalculatorFragment(), CalculatorFragment.TAG)
    }

    override fun onBackStackChange(fragment: Fragment) {
        currentFragment = fragment
        when (fragment) {
            is CalculatorFragment -> bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
            is FragmentHistory -> bnv_main.menu.findItem(R.id.item_history).isChecked = true
            is FragmentSettings -> bnv_main.menu.findItem(R.id.item_settings).isChecked = true
        }
    }

    private fun attachFragment(@IdRes containerId: Int, fragmentInstance: Fragment, tag: String?) {
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

            fragmentTransaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (currentFragment is CalculatorFragment) {
            if (System.currentTimeMillis() - timeFirstBack < 2000 && timeFirstBack != 0L) {
                finish()
            } else {
                timeFirstBack = System.currentTimeMillis()
                toast(getString(R.string.activity_main_exit_toast))
            }
        } else {
            onFirstOpen()
        }
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewMain : ViewBase {
    fun onFirstOpen()
    fun showMessage(message: String)
}