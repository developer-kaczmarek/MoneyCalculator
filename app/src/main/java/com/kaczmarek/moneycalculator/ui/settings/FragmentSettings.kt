package com.kaczmarek.moneycalculator.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.CLASSIC
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.FOURTEEN_DAYS
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.INDEFINITELY
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.NUMPAD
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.THIRTY_DAYS
import com.kaczmarek.moneycalculator.ui.base.FragmentBase
import com.kaczmarek.moneycalculator.ui.base.ViewBase
import com.kaczmarek.moneycalculator.ui.main.BackStackChangeListenerMain
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.ub.utils.dpToPx
import com.ub.utils.visible
import kotlinx.android.synthetic.main.component_toolbar.*
import kotlinx.android.synthetic.main.fragment_settings.*
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
class FragmentSettings : FragmentBase(),
    ViewSettings, View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    @InjectPresenter
    lateinit var presenter: PresenterSettings
    private var navigationListener: ExternalNavigation? = null
    private var stateStoragePeriod = INDEFINITELY
    private var stateKeyboardLayout = CLASSIC
    private var stateAlwaysOnDisplay = false
    private var isNewChange = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as? ExternalNavigation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
            (activity as? BackStackChangeListenerMain)?.onBackStackChange(this)
        }
        presenter.getAllBanknotes()
        iv_toolbar_action.setOnClickListener(this)
        rg_settings_history.setOnCheckedChangeListener(this)
        rg_settings_keyboard.setOnCheckedChangeListener(this)
        sw_settings_display.setOnCheckedChangeListener { _, isSelected ->
            isNewChange = true
            stateAlwaysOnDisplay = isSelected
        }

        stateStoragePeriod = presenter.getHistoryStoragePeriod()
        when (stateStoragePeriod) {
            INDEFINITELY -> rg_settings_history.check(R.id.rb_save_indefinitely)
            FOURTEEN_DAYS -> rg_settings_history.check(R.id.rb_save_fourteen_days)
            else -> rg_settings_history.check(R.id.rb_save_thirty_days)
        }
        stateKeyboardLayout = presenter.getKeyboardLayout()
        when (stateKeyboardLayout) {
            NUMPAD -> rg_settings_keyboard.check(R.id.rb_numpad_keyboard)
            else -> rg_settings_keyboard.check(R.id.rb_phone_keyboard)
        }
        stateAlwaysOnDisplay = presenter.isAlwaysOnDisplay()
        sw_settings_display.isChecked = stateAlwaysOnDisplay
    }

    override fun onStart() {
        super.onStart()
        setTitle(R.string.activity_main_title_settings_item)
        iv_toolbar_action.visible
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    override fun loadBanknotes() {
        presenter.components.clear()
        presenter.banknotes.forEach {
            val banknoteCheckBox = CheckBox(context)
            banknoteCheckBox.id = it.id
            if (it.name >= 1) {
                banknoteCheckBox.text = getString(R.string.common_ruble_format, it.name.toInt())
            } else {
                banknoteCheckBox.text =
                    getString(R.string.common_penny_format, (it.name * 100).toInt())
            }

            banknoteCheckBox.isChecked = it.isShow
            banknoteCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            banknoteCheckBox.typeface =
                ResourcesCompat.getFont(banknoteCheckBox.context, R.font.gotham_pro)
            banknoteCheckBox.setPadding(banknoteCheckBox.context.dpToPx(16).toInt(), 0, 0, 0)
            ll_settings_banknotes.addView(banknoteCheckBox)
            presenter.components.add(banknoteCheckBox)
            banknoteCheckBox.setOnCheckedChangeListener { view, isChecked ->
                isNewChange = true
                presenter.banknotes[presenter.components.indexOf(view)].isShow = isChecked
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_toolbar_action -> {
                if (isNewChange) {
                    if (presenter.areAllBanknotesInvisible()) {
                        showMessage(
                            getString(R.string.fragment_settings_all_banknotes_invisible)
                        )
                    } else {
                        presenter.saveVisibilityBanknotes()
                        presenter.saveHistoryStoragePeriod(stateStoragePeriod)
                        presenter.saveKeyboardLayout(stateKeyboardLayout)
                        presenter.saveAlwaysOnDisplay(stateAlwaysOnDisplay)
                        showMessage(getString(R.string.fragment_settings_save_successful))
                    }
                } else {
                    showMessage(getString(R.string.fragment_settings_no_new_changes))
                }

            }
        }
    }

    override fun onCheckedChanged(rg: RadioGroup?, checkedId: Int) {
        when (rg?.id) {
            R.id.rg_settings_history -> {
                isNewChange = true
                stateStoragePeriod = when (checkedId) {
                    R.id.rb_save_indefinitely -> INDEFINITELY
                    R.id.rb_save_fourteen_days -> FOURTEEN_DAYS
                    else -> THIRTY_DAYS
                }
            }
            R.id.rg_settings_keyboard -> {
                isNewChange = true
                stateKeyboardLayout = when (checkedId) {
                    R.id.rb_numpad_keyboard -> NUMPAD
                    else -> CLASSIC
                }
            }
        }
    }

    companion object {
        const val TAG = "SettingsFragment"
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewSettings : ViewBase {
    fun showMessage(message: String)
    fun loadBanknotes()
}