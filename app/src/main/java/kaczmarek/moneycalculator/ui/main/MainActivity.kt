package kaczmarek.moneycalculator.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.ActivityMainBinding
import kaczmarek.moneycalculator.ui.base.BaseActivity
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import kaczmarek.moneycalculator.ui.history.HistoryFragment
import kaczmarek.moneycalculator.ui.settings.SettingsFragment
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.toast
import moxy.ktx.moxyPresenter

interface MainView : ViewBase {
    fun onFirstOpen()
    fun showMessage(message: String)
}

class MainActivity : BaseActivity(R.layout.activity_main), MainView {

    private val presenter by moxyPresenter { MainPresenter() }

    var timeFirstBack = 0L
    var tapTargetView: TapTargetView? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvMain.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> {
                    openFragment(CalculatorFragment(), CalculatorFragment.TAG)
                    true
                }
                R.id.item_history -> {
                    openFragment(HistoryFragment(), HistoryFragment.TAG)
                    true
                }
                R.id.item_settings -> {
                    openFragment(SettingsFragment(), SettingsFragment.TAG)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Метод для управления кнопкой Назад
     * В случае, если при клике на Назад, приложение находится на фрагменте с калькулятором
     * необходимо отобразить сообщение о повторном клике на кнопку в течение 2х секунд
     * чтобы выйти из приложения, в противном случае выход не произойдет.
     * Если приложение находилось на фрагменте истории или настроек произойдет переход
     * на фрагмент с калькулятором
     */
    override fun onBackPressed() {
        if (supportFragmentManager.primaryNavigationFragment is CalculatorFragment) {
            if (System.currentTimeMillis() - timeFirstBack < 2000 && timeFirstBack != 0L) {
                finish()
            } else {
                timeFirstBack = System.currentTimeMillis()
                toast(getString(R.string.activity_main_exit_toast))
            }
        } else {
            tapTargetView?.gone
            openFragment(CalculatorFragment(), CalculatorFragment.TAG)
        }
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    /**
     * Метод вызывается при первом открытии приложения и открывает фрагмент с калькулятором
     */
    override fun onFirstOpen() {
        openFragment(CalculatorFragment(), CalculatorFragment.TAG, true)
    }

    /**
     * Метод для переключения фрагментов
     * @param fragmentInstance инстанс фрагмента на который произойдет переход,
     * @param tag метка фрагмента, по которой можно будет производить сверку текущего фрагмента
     * @param isFirstOpen флаг является ли текущий фрагмент самым первым
     */
    fun openFragment(fragmentInstance: Fragment, tag: String, isFirstOpen: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isFirstOpen) {
            fragmentTransaction.add(binding.flMainContainer.id, fragmentInstance, tag)
            fragmentTransaction.setPrimaryNavigationFragment(fragmentInstance)
        } else if (supportFragmentManager.primaryNavigationFragment?.tag != tag) {
            fragmentTransaction.replace(binding.flMainContainer.id, fragmentInstance, tag)
            fragmentTransaction.setPrimaryNavigationFragment(fragmentInstance)
        }
        when (fragmentInstance) {
            is SettingsFragment -> binding.bnvMain.menu.findItem(R.id.item_settings).isChecked = true
            is HistoryFragment -> binding.bnvMain.menu.findItem(R.id.item_history).isChecked = true
            else -> binding.bnvMain.menu.findItem(R.id.item_calculator).isChecked = true
        }
        fragmentTransaction.commit()
    }
}