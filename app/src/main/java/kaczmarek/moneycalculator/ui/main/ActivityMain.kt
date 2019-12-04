package kaczmarek.moneycalculator.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.ActivityBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import kaczmarek.moneycalculator.ui.history.FragmentHistory
import kaczmarek.moneycalculator.ui.settings.FragmentSettingsLicenses
import kaczmarek.moneycalculator.ui.settings.FragmentSettingsOverview
import kaczmarek.moneycalculator.utils.ExternalNavigation
import kaczmarek.moneycalculator.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

class ActivityMain : ActivityBase(),
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
                    attachFragment(CalculatorFragment(), CalculatorFragment.TAG)
                    true
                }
                R.id.item_history -> {
                    presenter.addFragmentToStack(R.id.item_history)
                    attachFragment(FragmentHistory(), FragmentHistory.TAG)
                    true
                }
                R.id.item_settings -> {
                    presenter.addFragmentToStack(R.id.item_settings)
                    attachFragment(FragmentSettingsOverview(), FragmentSettingsOverview.TAG)
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
        attachFragment(CalculatorFragment(), CalculatorFragment.TAG)
    }

    override fun onBackStackChange(fragment: Fragment) {
        currentFragment = fragment
        when (fragment) {
            is CalculatorFragment -> bnv_main.menu.findItem(R.id.item_calculator).isChecked = true
            is FragmentHistory -> bnv_main.menu.findItem(R.id.item_history).isChecked = true
            is FragmentSettingsOverview -> bnv_main.menu.findItem(R.id.item_settings).isChecked = true
        }
    }

    fun attachFragment(
        fragmentInstance: Fragment,
        tag: String?,
        isAddToBackStack: Boolean = false
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = fragmentInstance
            fragmentTransaction.add(fl_main_container.id, fragment, tag)
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

    override fun onBackPressed() {
        if (currentFragment is CalculatorFragment) {
            if (System.currentTimeMillis() - timeFirstBack < 2000 && timeFirstBack != 0L) {
                finish()
            } else {
                timeFirstBack = System.currentTimeMillis()
                toast(getString(R.string.activity_main_exit_toast))
            }
        } else if (currentFragment is FragmentSettingsLicenses) {
            super.onBackPressed()
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