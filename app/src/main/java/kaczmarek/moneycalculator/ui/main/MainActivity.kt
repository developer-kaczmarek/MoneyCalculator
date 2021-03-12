package kaczmarek.moneycalculator.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.getkeepsafe.taptargetview.TapTargetView
import com.google.android.material.snackbar.Snackbar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.ActivityMainBinding
import kaczmarek.moneycalculator.ui.base.BaseActivity
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import kaczmarek.moneycalculator.ui.history.HistoryFragment
import kaczmarek.moneycalculator.ui.settings.SettingsFragment
import kaczmarek.moneycalculator.utils.gone
import moxy.ktx.moxyPresenter

interface MainView : ViewBase {
    fun onFirstOpen()
}

class MainActivity : BaseActivity(R.layout.activity_main), MainView {

    var timeFirstBack = 0L
    var tapTargetView: TapTargetView? = null

    private lateinit var binding: ActivityMainBinding

    @Suppress("unused")
    private val presenter by moxyPresenter { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvMain.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.item_calculator -> openFragment(CalculatorFragment(), CalculatorFragment.TAG)
                R.id.item_history -> openFragment(HistoryFragment(), HistoryFragment.TAG)
                R.id.item_settings -> openFragment(SettingsFragment(), SettingsFragment.TAG)
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
                showMessage(getString(R.string.activity_main_exit_toast))
            }
        } else {
            tapTargetView?.gone
            openFragment(CalculatorFragment(), CalculatorFragment.TAG)
        }
    }

    /**
     * Метод для отображения SnackBar
     */
    override fun showMessage(message: String) {
        Snackbar.make(binding.clMainContainer, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Метод вызывается при первом открытии Activity и открывает фрагмент с калькулятором
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
    fun openFragment(
        fragmentInstance: Fragment,
        tag: String,
        isFirstOpen: Boolean = false
    ): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isFirstOpen || supportFragmentManager.primaryNavigationFragment?.tag != tag) {
            with(fragmentTransaction) {
                if (isFirstOpen)
                    add(binding.flMainContainer.id, fragmentInstance, tag)
                else
                    replace(binding.flMainContainer.id, fragmentInstance, tag)
                setPrimaryNavigationFragment(fragmentInstance)
            }
        }
        binding.bnvMain.menu.findItem(
            when (fragmentInstance) {
                is SettingsFragment -> R.id.item_settings
                is HistoryFragment -> R.id.item_history
                else -> R.id.item_calculator
            }
        ).isChecked = true
        fragmentTransaction.commit()
        return true
    }
}