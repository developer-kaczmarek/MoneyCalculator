package kaczmarek.moneycalculator.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.component.keyboard.Keyboard.Companion.CLASSIC
import kaczmarek.moneycalculator.component.keyboard.Keyboard.Companion.NUMPAD
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.FOURTEEN_DAYS
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.INDEFINITELY
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.THIRTY_DAYS
import kaczmarek.moneycalculator.ui.base.BaseCheckChangeListener
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.calculator.CalculatorFragment
import kaczmarek.moneycalculator.ui.main.MainActivity
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */

interface SettingsView : ViewBase {
    fun setVisibilityBanknotes(list: List<SettingBanknoteItem>)
    fun showContent()
    fun returnToCalculator()
}

class SettingsFragment : BaseFragment(R.layout.fragment_settings), SettingsView,
    View.OnClickListener, BaseCheckChangeListener, RadioGroup.OnCheckedChangeListener {

    private var stateAlwaysOnDisplay = false
    private var isNewChange = false
    private var stateStoragePeriod = INDEFINITELY
    private var stateKeyboardLayout = CLASSIC
    private var adapter: SettingsBanknotesRVAdapter? = null
    private lateinit var rgSettingsHistory: RadioGroup
    private lateinit var rgSettingsKeyboard: RadioGroup
    private lateinit var swSettingsDisplay: SwitchCompat
    private lateinit var rvSettingsBanknotes: RecyclerView

    private val presenter by moxyPresenter { SettingsPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getAllBanknotes()
        rgSettingsHistory = view.findViewById(R.id.rg_settings_history)
        rgSettingsKeyboard = view.findViewById(R.id.rg_settings_keyboard)
        swSettingsDisplay = view.findViewById(R.id.sw_settings_display)
        rvSettingsBanknotes = view.findViewById(R.id.rv_settings_banknotes)
        val ivToolbarAction = view.findViewById<ImageView>(R.id.iv_toolbar_action)
        val tvSettingsFeedback = view.findViewById<TextView>(R.id.tv_settings_feedback)
        val tvSettingsRateApp = view.findViewById<TextView>(R.id.tv_settings_rate_app)
        val tvSettingsGithub = view.findViewById<TextView>(R.id.tv_settings_github)
        val tvSettingsVersions = view.findViewById<TextView>(R.id.tv_settings_versions)
        ivToolbarAction.setOnClickListener(this)
        rgSettingsHistory.setOnCheckedChangeListener(this)
        rgSettingsKeyboard.setOnCheckedChangeListener(this)
        tvSettingsFeedback.setOnClickListener(this)
        tvSettingsRateApp.setOnClickListener(this)
        tvSettingsGithub.setOnClickListener(this)
        swSettingsDisplay.setOnCheckedChangeListener { _, isSelected ->
            isNewChange = true
            stateAlwaysOnDisplay = isSelected
        }

        val packageInfo = context?.packageManager?.getPackageInfo(view.context.packageName, 0)
        tvSettingsVersions.text = getString(
            R.string.fragment_settings_versions,
            packageInfo?.versionName
        )
        if (presenter.getCountMeetComponents() == 3) meetAppOnSettings(view)
        adapter = SettingsBanknotesRVAdapter().apply {
            checkChangeListener = this@SettingsFragment
        }
        rvSettingsBanknotes.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        stateStoragePeriod = presenter.getHistoryStoragePeriod()

        rgSettingsHistory.check(
            when (stateStoragePeriod) {
                INDEFINITELY -> R.id.rb_settings_save_indefinitely
                FOURTEEN_DAYS -> R.id.rb_settings_save_fourteen_days
                else -> R.id.rb_settings_save_thirty_days
            }
        )

        stateKeyboardLayout = presenter.getKeyboardLayout()

        rgSettingsKeyboard.check(
            when (stateKeyboardLayout) {
                NUMPAD -> R.id.rb_settings_numpad_keyboard
                else -> R.id.rb_settings_phone_keyboard
            }
        )

        stateAlwaysOnDisplay = presenter.isAlwaysOnDisplay()

        swSettingsDisplay.isChecked = stateAlwaysOnDisplay
    }

    /**
     * Метод для обновления списка чекбоксов в RecyclerView
     * для настройки видимости карточек банкнот на экране Калькулятора
     */
    override fun setVisibilityBanknotes(list: List<SettingBanknoteItem>) {
        adapter?.update(list)
    }

    /**
     * Метод для запуска анимации плавного появления контента после прогрузки
     */
    override fun showContent() {

    }

    /**
     * Метод для возвращения на экран калькулятора, вызывается
     * после окончания знакомства с элементами приложения
     */
    override fun returnToCalculator() {
        (activity as MainActivity).openFragment(CalculatorFragment(), CalculatorFragment.TAG)
    }

    /**
     * Метод для обработки нажатия на View
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_toolbar_action -> {
                when {
                    isNewChange && presenter.isAllBanknotesInvisible() -> showMessage(getString(R.string.fragment_settings_all_banknotes_invisible))
                    isNewChange && !presenter.isAllBanknotesInvisible() -> presenter.saveAllSettings(
                        stateStoragePeriod,
                        stateKeyboardLayout,
                        stateAlwaysOnDisplay
                    )
                    else -> showMessage(getString(R.string.fragment_settings_no_new_changes))
                }
            }
            R.id.tv_settings_feedback -> {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", getString(R.string.fragment_settings_email_title), null)
                )
                if (context?.packageManager?.resolveActivity(emailIntent, 0) != null) {
                    startActivity(
                        Intent.createChooser(
                            emailIntent,
                            getString(R.string.fragment_settings_mail_chooser_title)
                        )
                    )
                } else {
                    showMessage(getString(R.string.fragment_settings_app_for_intent_not_found))
                }
            }
            R.id.tv_settings_rate_app -> {
                val packageName = activity?.packageName
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }
            }
            R.id.tv_settings_github -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(getString(R.string.fragment_settings_url_github))
                if (context?.packageManager?.resolveActivity(i, 0) != null) {
                    startActivity(i)
                } else {
                    showMessage(message = getString(R.string.fragment_settings_app_for_intent_not_found))
                }
            }
        }
    }

    /**
     * Метод для обработки изменения состояния радиокнопок внутри RadioGroup
     */
    override fun onCheckedChanged(rg: RadioGroup, checkedId: Int) {
        when (rg.id) {
            R.id.rg_settings_history -> {
                isNewChange = true
                stateStoragePeriod = when (checkedId) {
                    R.id.rb_settings_save_indefinitely -> INDEFINITELY
                    R.id.rb_settings_save_fourteen_days -> FOURTEEN_DAYS
                    else -> THIRTY_DAYS
                }
            }
            R.id.rg_settings_keyboard -> {
                isNewChange = true
                stateKeyboardLayout = when (checkedId) {
                    R.id.rb_settings_numpad_keyboard -> NUMPAD
                    else -> CLASSIC
                }
            }
        }
    }

    /**
     * Метод для обработки изменения состояния чекбокса в Recyclerview
     */
    override fun onChange(view: View, position: Int, isCheck: Boolean) {
        if (view.id == R.id.cb_settings_banknote_visibility) {
            isNewChange = true
            presenter.banknotes[position] = presenter.banknotes[position].copy(isShow = isCheck)
        }
    }

    private fun meetAppOnSettings(viewFragment: View) {
        (activity as MainActivity).tapTargetView = TapTargetView.showFor(activity,
            TapTarget.forView(
                viewFragment.findViewById(R.id.iv_toolbar_action),
                getString(R.string.fragment_calculator_title_component_save),
                getString(R.string.fragment_calculator_description_component_save_settings)
            )
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(R.color.black_30)
                .outerCircleAlpha(0.93f)
                .textColor(R.color.white)
                .descriptionTextSize(14)
                .titleTextSize(18)
                .textTypeface(ResourcesCompat.getFont(viewFragment.context, R.font.gotham_pro))
                .drawShadow(true)
                .cancelable(false)
                .transparentTarget(true)
                .targetRadius(30),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView) {
                    super.onTargetClick(view)
                    presenter.updateCountMeetComponent(4)
                }
            })
    }

    companion object {
        const val TAG = "SettingsFragment"
    }
}