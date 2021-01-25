package kaczmarek.moneycalculator.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.FragmentCalculatorBinding
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.MainActivity
import kaczmarek.moneycalculator.ui.settings.SettingsFragment
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

interface CalculatorView : ViewBase {
    fun addBanknoteCard()
    fun updateTotalAmount()
    fun showMessage(message: String)
}

class CalculatorFragment : BaseFragment(R.layout.fragment_calculator), CalculatorView,
    View.OnClickListener, View.OnLongClickListener {

    private val presenter by moxyPresenter { CalculatorPresenter() }
    private var focusedEditTextId = 0
    private var countMeetComponent = 0
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSave.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
        binding.ivNext.setOnClickListener(this)
        with(binding.ivDelete) {
            setOnClickListener(this@CalculatorFragment)
            setOnLongClickListener(this@CalculatorFragment)
        }
        binding.bDigit00.setOnClickListener(this)
        binding.bDigit01.setOnClickListener(this)
        binding.bDigit02.setOnClickListener(this)
        binding.bDigit10.setOnClickListener(this)
        binding.bDigit11.setOnClickListener(this)
        binding.bDigit12.setOnClickListener(this)
        binding.bDigit20.setOnClickListener(this)
        binding.bDigit21.setOnClickListener(this)
        binding.bDigit22.setOnClickListener(this)
        binding.bDigit31.setOnClickListener(this)

       /* if (presenter.getKeyboardLayout() == NUMPAD) {
            binding.bDigit00.setText(R.string.digit_7)
            binding.bDigit01.setText(R.string.digit_8)
            binding.bDigit02.setText(R.string.digit_9)
            binding.bDigit20.setText(R.string.digit_1)
            binding.bDigit21.setText(R.string.digit_2)
            binding.bDigit22.setText(R.string.digit_3)
        }

        if (presenter.howMuchKnowComponents() <= 2) {
            countMeetComponent = presenter.howMuchKnowComponents()
            nextMeeting(view)
        }*/
    }

    override fun onStart() {
        super.onStart()
        val flags = activity?.window?.attributes?.flags
        /*if (presenter.isAlwaysOnDisplay()) {
            if (flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) == 0) {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        } else {
            if (flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) != 0) {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
        presenter.getBanknotes()
        presenter.updateTotalAmount()*/
    }

    override fun onDestroyView() {
      //  presenter.setCalculatorItems()
        super.onDestroyView()
        _binding = null
    }

    override fun addBanknoteCard() {
        context?.let { context ->
            binding.llContainerComponents.removeAllViews()
            /*presenter.components.clear()
            presenter.banknotes.forEach { banknote ->
                val componentCard = BanknoteCard(context).apply {
                    setValueBanknote(banknote.name)
                    setCount(banknote.count)
                    if (banknote.count != 0) addDigit(banknote.count.toString())
                    setColorTheme(banknote.backgroundColor, banknote.textColor)
                    editTextBanknoteCount.setOnFocusChangeListener { _, hasFocus ->
                        focusedEditTextId = presenter.components.indexOf(this)
                        setHideHint(hasFocus)
                    }
                }
                presenter.components.add(componentCard)
                binding.llContainerComponents.addView(
                    componentCard, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }*/
            updateStateControlPanel()
        }
    }

    override fun onLongClick(v: View): Boolean {
        return if (v.id == R.id.iv_delete) {
           /* presenter.components.forEachIndexed { index, componentCard ->
                with(componentCard) {
                    editTextBanknoteCount.text.clear()
                    setCount(0)
                }
                presenter.banknotes[index].count = componentCard.getCount()
                presenter.banknotes[index].amount = componentCard.getAmount()
            }
            presenter.updateTotalAmount()
            scrollById(0)
            showMessage(getString(R.string.fragment_calculator_all_delete_values))*/
            true
        } else {
            false
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> {
                if (focusedEditTextId != 0) scrollById(idComponent = focusedEditTextId - 1)
                updateStateControlPanel()
            }
           /* R.id.iv_next -> {
                if (focusedEditTextId != presenter.components.size - 1) scrollById(idComponent = focusedEditTextId + 1)
                updateStateControlPanel()
            }
            R.id.iv_delete -> {
                presenter.components[focusedEditTextId].deleteDigit()
                scrollById(idComponent = focusedEditTextId)
            }
            R.id.iv_save -> presenter.saveSession()*/
            else -> clickDigit(focusedEditTextId, view as Button)
        }

   /*     presenter.banknotes[focusedEditTextId].amount =
            presenter.components[focusedEditTextId].getAmount()
        presenter.banknotes[focusedEditTextId].count =
            presenter.components[focusedEditTextId].getCount()
        presenter.updateTotalAmount()*/
    }

    override fun updateTotalAmount() {
        /*binding.tvTotalAmount.text = if (presenter.isTotalAmountInteger()) String.format(
            getString(R.string.common_ruble_format),
            presenter.totalAmount.toInt()
        ) else String.format(getString(R.string.common_ruble_float_format), presenter.totalAmount)*/
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    private fun clickDigit(idComponent: Int, button: Button) {
        scrollById(idComponent = idComponent)
     //   presenter.components[idComponent].addDigit(button.text.toString())
    }

    private fun scrollById(idComponent: Int) {
        val outLocation = IntArray(2)
        focusedEditTextId = idComponent
     /*   presenter.components[focusedEditTextId].getLocationOnScreen(outLocation)
        binding.hsvCalculator.smoothScrollBy(outLocation[0], 0)
        presenter.components[focusedEditTextId].editTextBanknoteCount.requestFocus()*/

    }

    private fun updateStateControlPanel() {
        /*if (presenter.components.size == 1) {
            binding.ivBack.isEnabled = false
            binding.ivNext.isEnabled = false
        } else {
            binding.ivBack.isEnabled = focusedEditTextId != 0
            binding.ivNext.isEnabled = focusedEditTextId != presenter.components.size - 1
        }*/
    }

    private fun meetAppOnCalculator(
        viewFragment: View, @IdRes idRes: Int,
        title: String,
        description: String,
        targetRadius: Int
    ) {
        (activity as MainActivity).tapTargetView = TapTargetView.showFor(activity,
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
                     //   presenter.updateCountMeetComponent(countMeetComponent)
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
                150
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
               // presenter.updateCountMeetComponent(countMeetComponent)
                (activity as MainActivity).openFragment(
                    SettingsFragment(),
                    SettingsFragment.TAG
                )
            }
        }
    }

    companion object {
        const val TAG = "CalculatorFragment"
    }
}