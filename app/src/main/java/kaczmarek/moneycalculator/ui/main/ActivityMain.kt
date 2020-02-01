package kaczmarek.moneycalculator.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.ActivityBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import kaczmarek.moneycalculator.ui.history.FragmentHistory
import kaczmarek.moneycalculator.ui.settings.FragmentSettingsOverview
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

class ActivityMain : ActivityBase(),
    ViewMain,
    BackStackChangeListenerMain {

    @InjectPresenter
    lateinit var presenter: PresenterMain
    var currentFragment: Fragment? = null
    var timeFirstBack = 0L
    var tapTargetView: TapTargetView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> {
                    attachFragment(CalculatorFragment(), CalculatorFragment.TAG)
                    true
                }
                R.id.item_history -> {
                    attachFragment(FragmentHistory(), FragmentHistory.TAG)
                    true
                }
                R.id.item_settings -> {
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


    override fun onFirstOpen() {
        attachFragment(CalculatorFragment(), CalculatorFragment.TAG, true)
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
        isFirstOpen: Boolean = false
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isFirstOpen) {
            fragmentTransaction.add(fl_main_container.id, fragmentInstance, tag)
        } else {
            if (currentFragment?.tag != tag) {
                fragmentTransaction.replace(fl_main_container.id, fragmentInstance, tag)
            }
        }
        fragmentTransaction.commit()
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
            tapTargetView?.gone
            onFirstOpen()
        }
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewMain : ViewBase {
    fun onFirstOpen()
    fun showMessage(message: String)
}