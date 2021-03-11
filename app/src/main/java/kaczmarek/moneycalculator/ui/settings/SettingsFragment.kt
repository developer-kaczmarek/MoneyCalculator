package kaczmarek.moneycalculator.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.FragmentSettingsBinding
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.CLASSIC
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.FOURTEEN_DAYS
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.INDEFINITELY
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.NUMPAD
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

    private val presenter by moxyPresenter { SettingsPresenter() }
    private var stateAlwaysOnDisplay = false
    private var isNewChange = false
    private var stateStoragePeriod = INDEFINITELY
    private var stateKeyboardLayout = CLASSIC
    private var adapter: SettingsBanknotesRVAdapter? = null
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getAllBanknotes()
        binding.ivToolbarAction.setOnClickListener(this)
        binding.rgSettingsHistory.setOnCheckedChangeListener(this)
        binding.rgSettingsKeyboard.setOnCheckedChangeListener(this)
        binding.tvSettingsFeedback.setOnClickListener(this)
        binding.tvSettingsRateApp.setOnClickListener(this)
        binding.tvSettingsGithub.setOnClickListener(this)
        binding.swSettingsDisplay.setOnCheckedChangeListener { _, isSelected ->
            isNewChange = true
            stateAlwaysOnDisplay = isSelected
        }

        val packageInfo = context?.packageManager?.getPackageInfo(view.context.packageName, 0)
        binding.tvSettingsVersions.text =
            getString(R.string.fragment_settings_versions, packageInfo?.versionName)
        if (presenter.getCountMeetComponents() == 3) meetAppOnSettings(view)
        adapter = SettingsBanknotesRVAdapter().apply {
            checkChangeListener = this@SettingsFragment
        }
        binding.rvSettingsBanknotes.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        stateStoragePeriod = presenter.getHistoryStoragePeriod()

        binding.rgSettingsHistory.check(
            when (stateStoragePeriod) {
                INDEFINITELY -> R.id.rb_save_indefinitely
                FOURTEEN_DAYS -> R.id.rb_save_fourteen_days
                else -> R.id.rb_save_thirty_days
            }
        )

        stateKeyboardLayout = presenter.getKeyboardLayout()

        binding.rgSettingsKeyboard.check(
            when (stateKeyboardLayout) {
                NUMPAD -> R.id.rb_numpad_keyboard
                else -> R.id.rb_phone_keyboard
            }
        )

        stateAlwaysOnDisplay = presenter.isAlwaysOnDisplay()

        binding.swSettingsDisplay.isChecked = stateAlwaysOnDisplay
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setVisibilityBanknotes(list: List<SettingBanknoteItem>) {
        adapter?.update(list)
    }

    override fun showContent() {
        binding.nsvSettings.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            start()
        }
    }

    override fun returnToCalculator() {
        (activity as MainActivity).openFragment(CalculatorFragment(), CalculatorFragment.TAG)
    }

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

    override fun onCheckedChanged(rg: RadioGroup, checkedId: Int) {
        when (rg.id) {
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

    override fun onChange(view: View, position: Int, isCheck: Boolean) {
        if (view.id == R.id.cb_banknote_visibility) {
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