package com.kaczmarek.moneycalculator.ui.settings.fragmens

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
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.CLASSIC
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.FOURTEEN_DAYS
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.INDEFINITELY
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.NUMPAD
import com.kaczmarek.moneycalculator.di.SettingsService.Companion.THIRTY_DAYS
import com.kaczmarek.moneycalculator.ui.base.fragmens.BaseFragment
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.ui.settings.presenters.SettingsPresenter
import com.kaczmarek.moneycalculator.ui.settings.views.SettingsView
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.ub.utils.dpToPx
import com.ub.utils.visible
import kotlinx.android.synthetic.main.component_toolbar.*
import kotlinx.android.synthetic.main.fragment_settings.*
import moxy.presenter.InjectPresenter

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
class SettingsFragment : BaseFragment(), SettingsView, View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter
    private var navigationListener: ExternalNavigation? = null
    private var stateStoragePeriod = INDEFINITELY
    private var stateKeyboardLayout = CLASSIC
    private var stateAlwaysOnDisplay = false

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
            (activity as? BackStackChangeListener)?.onBackStackChange(this)
        }
        presenter.getAllBanknotes()
        iv_toolbar_action.setOnClickListener(this)
        rg_settings_history.setOnCheckedChangeListener(this)
        rg_settings_keyboard.setOnCheckedChangeListener(this)
        sw_settings_display.setOnCheckedChangeListener { _, isSelected ->
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
        presenter.banknotes.forEach {
            val banknoteCheck = CheckBox(context)
            banknoteCheck.id = it.id
            if (it.name >= 1) {
                banknoteCheck.text = getString(R.string.common_ruble_format, it.name.toInt())
            } else {
                banknoteCheck.text =
                    getString(R.string.common_penny_format, (it.name * 100).toInt())
            }

            banknoteCheck.isChecked = it.isShow
            banknoteCheck.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            banknoteCheck.typeface =
                ResourcesCompat.getFont(banknoteCheck.context, R.font.gotham_pro)
            banknoteCheck.setPadding(banknoteCheck.context.dpToPx(16).toInt(), 0, 0, 0)
            ll_settings_banknotes.addView(banknoteCheck)

            banknoteCheck.setOnCheckedChangeListener { view, isChecked ->
                presenter.banknotes[view.id].isShow = isChecked
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_toolbar_action -> {
                presenter.saveVisibilityBanknotes()
                presenter.saveHistoryStoragePeriod(stateStoragePeriod)
                presenter.saveKeyboardLayout(stateKeyboardLayout)
                presenter.saveAlwaysOnDisplay(stateAlwaysOnDisplay)
            }
        }
    }

    override fun onCheckedChanged(rg: RadioGroup?, checkedId: Int) {
        when (rg?.id) {
            R.id.rg_settings_history -> {
                stateStoragePeriod = when (checkedId) {
                    R.id.rb_save_indefinitely -> INDEFINITELY
                    R.id.rb_save_fourteen_days -> FOURTEEN_DAYS
                    else -> THIRTY_DAYS
                }
            }
            R.id.rg_settings_keyboard -> {
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