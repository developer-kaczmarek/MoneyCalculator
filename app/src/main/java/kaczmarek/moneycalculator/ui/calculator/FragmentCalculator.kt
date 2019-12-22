package kaczmarek.moneycalculator.ui.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.di.services.SettingsService.Companion.NUMPAD
import kaczmarek.moneycalculator.ui.base.FragmentBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.ActivityMain
import kaczmarek.moneycalculator.ui.main.BackStackChangeListenerMain
import kaczmarek.moneycalculator.ui.settings.FragmentSettingsOverview
import kaczmarek.moneycalculator.utils.BanknoteCard
import kaczmarek.moneycalculator.utils.ExternalNavigation
import kotlinx.android.synthetic.main.fragment_calculator.*
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import kotlin.math.floor

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class CalculatorFragment : FragmentBase(), ViewCalculator,
    View.OnClickListener, View.OnLongClickListener {

    @InjectPresenter
    lateinit var presenter: PresenterCalculator
    private var navigationListener: ExternalNavigation? = null
    private var focusedEditTextId = 0
    private var countMeetComponent = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as? ExternalNavigation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onDestroyView() {
        presenter.setCalculatorItems()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
            (activity as? BackStackChangeListenerMain)?.onBackStackChange(this)
        }
        iv_save.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        iv_next.setOnClickListener(this)
        iv_delete.setOnClickListener(this)
        iv_delete.setOnLongClickListener(this)
        b_digit_0_0.setOnClickListener(this)
        b_digit_0_1.setOnClickListener(this)
        b_digit_0_2.setOnClickListener(this)
        b_digit_1_0.setOnClickListener(this)
        b_digit_1_1.setOnClickListener(this)
        b_digit_1_2.setOnClickListener(this)
        b_digit_2_0.setOnClickListener(this)
        b_digit_2_1.setOnClickListener(this)
        b_digit_2_2.setOnClickListener(this)
        b_digit_3_1.setOnClickListener(this)

        if (presenter.getKeyboardLayout() == NUMPAD) {
            b_digit_0_0.setText(R.string.digit_7)
            b_digit_0_1.setText(R.string.digit_8)
            b_digit_0_2.setText(R.string.digit_9)
            b_digit_2_0.setText(R.string.digit_1)
            b_digit_2_1.setText(R.string.digit_2)
            b_digit_2_2.setText(R.string.digit_3)
        }

        if (presenter.howMuchKnowComponents() <= 2) {
            countMeetComponent = presenter.howMuchKnowComponents()
            nextMeeting(view)
        }
    }

    override fun onStart() {
        super.onStart()
        val flags = activity?.window?.attributes?.flags
        if (presenter.isAlwaysOnDisplay()) {
            if (flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) == 0) {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        } else {
            if (flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) != 0) {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
        presenter.getBanknotes()
        presenter.updateTotalAmount()
    }

    override fun addBanknoteCard() {
        context?.let { context ->
            presenter.components.clear()
            presenter.banknotes.forEach { banknote ->
                val componentCard = BanknoteCard(context)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                componentCard.setValueBanknote(banknote.name)
                componentCard.setCount(banknote.count)
                if (banknote.count != 0) {
                    componentCard.addDigit(banknote.count.toString())
                }
                componentCard.setColorTheme(banknote.backgroundColor, banknote.textColor)
                presenter.components.add(componentCard)
                componentCard.editTextBanknoteCount.setOnFocusChangeListener { _, hasFocus ->
                    focusedEditTextId = presenter.components.indexOf(componentCard)
                    componentCard.setHideHint(hasFocus)
                }
                ll_container_components.addView(componentCard, layoutParams)
            }
            updateStateControlPanel()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        presenter.components.forEachIndexed { index, componentCard ->
            componentCard.editTextBanknoteCount.text.clear()
            componentCard.setCount(0)
            presenter.banknotes[index].count = componentCard.getCount()
            presenter.banknotes[index].amount = componentCard.getAmount()
        }
        presenter.updateTotalAmount()
        scrollById(0)
        showMessage(getString(R.string.fragment_calculator_all_delete_values))
        return true
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                if (focusedEditTextId != 0) {
                    scrollById(idComponent = focusedEditTextId - 1)
                }
                updateStateControlPanel()
            }
            R.id.iv_next -> {
                if (focusedEditTextId != presenter.components.size - 1) {
                    scrollById(idComponent = focusedEditTextId + 1)
                }
                updateStateControlPanel()
            }
            R.id.iv_delete -> {
                presenter.components[focusedEditTextId].deleteDigit()
                scrollById(idComponent = focusedEditTextId)
            }
            R.id.iv_save -> {
                presenter.saveSession()
            }
            else -> {
                clickDigit(focusedEditTextId, view as Button)
            }
        }

        presenter.banknotes[focusedEditTextId].amount =
            presenter.components[focusedEditTextId].getAmount()
        presenter.banknotes[focusedEditTextId].count =
            presenter.components[focusedEditTextId].getCount()
        presenter.updateTotalAmount()
    }

    override fun updateTotalAmount() {
        if (presenter.totalAmount - floor(presenter.totalAmount) == 0F) {
            tv_total_amount.text = String.format(
                getString(R.string.common_ruble_format),
                floor(presenter.totalAmount).toInt()
            )
        } else {
            tv_total_amount.text =
                String.format(getString(R.string.common_ruble_float_format), presenter.totalAmount)
        }
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    private fun clickDigit(idComponent: Int, button: Button) {
        scrollById(idComponent = idComponent)
        presenter.components[idComponent].addDigit(button.text.toString())
    }

    private fun scrollById(idComponent: Int) {
        val outLocation = IntArray(2)
        focusedEditTextId = idComponent
        presenter.components[focusedEditTextId].getLocationOnScreen(outLocation)
        hsv_calculator.smoothScrollBy(outLocation[0], 0)
        presenter.components[focusedEditTextId].editTextBanknoteCount.requestFocus()

    }

    private fun updateStateControlPanel() {
        if (presenter.components.size == 1) {
            iv_back.isEnabled = false
            iv_next.isEnabled = false
        } else {
            iv_back.isEnabled = focusedEditTextId != 0
            iv_next.isEnabled = focusedEditTextId != presenter.components.size - 1
        }
    }

    private fun meetAppOnCalculator(
        viewFragment: View, @IdRes idRes: Int,
        title: String,
        description: String,
        targetRadius: Int
    ) {
        TapTargetView.showFor(activity,
            TapTarget.forView(
                viewFragment.findViewById(idRes),
                title,
                description
            )
                .outerCircleColor(R.color.colorAccent)
                .outerCircleAlpha(0.93f)
                .textColor(R.color.white)
                .descriptionTextSize(14)
                .titleTextSize(18)
                .textTypeface(ResourcesCompat.getFont(viewFragment.context, R.font.gotham_pro))
                .drawShadow(true)
                .cancelable(false)
                .transparentTarget(true)
                .targetRadius(targetRadius),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView) {
                    super.onTargetClick(view)
                    if (countMeetComponent <= 2) {
                        presenter.updateCountMeetComponent(countMeetComponent)
                        countMeetComponent++
                        nextMeeting(viewFragment)
                    }

                }
            })
    }

    private fun nextMeeting(view: View) {
        when (countMeetComponent) {
            0 -> meetAppOnCalculator(
                view,
                R.id.ll_control_panel,
                getString(R.string.fragment_calculator_title_component_board),
                getString(R.string.fragment_calculator_description_component_board),
                180
            )
            1 -> {
                meetAppOnCalculator(
                    view,
                    R.id.iv_delete,
                    getString(R.string.fragment_calculator_title_component_delete),
                    getString(R.string.fragment_calculator_description_component_delete),
                    40
                )
            }
            2 -> {
                meetAppOnCalculator(
                    view,
                    R.id.iv_save,
                    getString(R.string.fragment_calculator_title_component_save),
                    getString(R.string.fragment_calculator_description_component_save_calculator),
                    40
                )
            }
            3 -> {
                presenter.updateCountMeetComponent(countMeetComponent)
                (activity as ActivityMain?)?.attachFragment(
                    FragmentSettingsOverview(),
                    FragmentSettingsOverview.TAG
                )
            }
        }
    }

    companion object {
        const val TAG = "CalculatorFragment"
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewCalculator : ViewBase {
    fun addBanknoteCard()
    fun updateTotalAmount()
    fun showMessage(message: String)
}