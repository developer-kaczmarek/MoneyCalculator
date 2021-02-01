package kaczmarek.moneycalculator.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.databinding.FragmentCalculatorBinding
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.NUMPAD
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.MainActivity
import kaczmarek.moneycalculator.ui.settings.SettingsFragment
import kaczmarek.moneycalculator.utils.BanknoteCard
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

interface CalculatorView : ViewBase {
    fun addBanknoteCard()
    fun setTotalAmount(stringAmount: String)
}

class CalculatorFragment : BaseFragment(R.layout.fragment_calculator), CalculatorView,
    View.OnClickListener, View.OnLongClickListener {

    private val presenter by moxyPresenter { CalculatorPresenter() }
    private var focusableCardIndex = 0
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

        if (presenter.getKeyboardLayout() == NUMPAD) {
            binding.bDigit00.setText(R.string.digit_7)
            binding.bDigit01.setText(R.string.digit_8)
            binding.bDigit02.setText(R.string.digit_9)
            binding.bDigit20.setText(R.string.digit_1)
            binding.bDigit21.setText(R.string.digit_2)
            binding.bDigit22.setText(R.string.digit_3)
        }

        /*if (presenter.getCountKnownComponents() <= 2) {
            countMeetComponent = presenter.getCountKnownComponents()
            startAcquaintanceWithComponent(view)
        }*/
    }

    override fun onStart() {
        super.onStart()
        val flags = activity?.window?.attributes?.flags
        if (presenter.isAlwaysBacklightOn() && flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) == 0) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        presenter.getVisibleBanknotes()

    }

    override fun onDestroyView() {
        //  presenter.setCalculatorItems()
        super.onDestroyView()
        _binding = null
    }

    override fun addBanknoteCard() {
        context?.let { context ->
            binding.llContainerComponents.removeAllViews()
            presenter.banknotes.forEachIndexed { index, item ->
                val banknoteCard = BanknoteCard(context).apply {
                    id = index
                    denomination = item.name
                    count = item.count
                    setBackgroundCard(item.backgroundColor)
                    focusChangeCardListener = View.OnFocusChangeListener { v, hasFocus ->
                        if (hasFocus) focusableCardIndex = id
                    }
                }
                binding.llContainerComponents.addView(
                    banknoteCard, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
        }
        updateStateControlPanel()
    }

    override fun onLongClick(v: View): Boolean {
        return if (v.id == R.id.iv_delete) {
            /*  presenter.components.forEachIndexed { index, componentCard ->
                  with(componentCard) {
                      editTextBanknoteCount.text.clear()
                      setCount(0)
                  }
                  presenter.banknotes[index].count = componentCard.getCount()
                  presenter.banknotes[index].amount = componentCard.getAmount()
              }
              presenter.updateTotalAmount()
              scrollById(0)*/
            showMessage(getString(R.string.fragment_calculator_all_delete_values))
            true
        } else {
            false
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> {
                /*  if (focusableCardIndex != 0) setFocusForSelectedCard(focusableCardIndex - 1)
                  updateStateControlPanel()*/
            }
            R.id.iv_next -> {
                /*if (focusableCardIndex != presenter.banknotes.size - 1) setFocusForSelectedCard(focusableCardIndex + 1)
                updateStateControlPanel()*/
            }
            R.id.iv_delete -> {
                /* presenter.components[focusedEditTextId].deleteDigit()
                 scrollById(idComponent = focusedEditTextId)*/
            }
            R.id.iv_save -> presenter.saveCurrentCalculatingSession()
            else -> {
                if (focusableCardIndex == -1) return
                presenter.banknotes[focusableCardIndex] =
                    presenter.banknotes[focusableCardIndex].copy(
                        count = (view as Button).text.toString().toInt()
                    )
                (binding.llContainerComponents[focusableCardIndex] as BanknoteCard).addDigit((view as Button).text)
                setFocusForSelectedCard()
            }
        }

        /*     presenter.banknotes[focusedEditTextId].amount =
                 presenter.components[focusedEditTextId].getAmount()
             presenter.banknotes[focusedEditTextId].count =
                 presenter.components[focusedEditTextId].getCount()
             presenter.updateTotalAmount()*/
    }

    override fun setTotalAmount(stringAmount: String) {
        binding.tvTotalAmount.text = stringAmount
    }

    private fun setFocusForSelectedCard() {
        val outLocation = IntArray(2)
        val card = binding.llContainerComponents[focusableCardIndex]
        card.getLocationOnScreen(outLocation)
        binding.hsvCalculator.smoothScrollBy(outLocation[0], 0)
    }

    private fun setFocusForSelectedCard(index: Int) {
        focusableCardIndex = index
        val outLocation = IntArray(2)
        val card = binding.llContainerComponents.findViewById<BanknoteCard>(focusableCardIndex)
        card.getLocationOnScreen(outLocation)
        binding.hsvCalculator.smoothScrollBy(outLocation[0], 0)
    }

    private fun updateStateControlPanel() {
        // binding.ivBack.isEnabled = presenter.banknotes.size == 1 || focusableCardIndex != 0
        //   binding.ivNext.isEnabled = presenter.banknotes.size == 1 || focusedEditTextId != presenter.components.size - 1
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
                        presenter.updateCountMeetComponent(countMeetComponent)
                        countMeetComponent++
                        startAcquaintanceWithComponent(viewFragment)
                    }
                }
            })
    }

    private fun startAcquaintanceWithComponent(view: View) {
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
                presenter.updateCountMeetComponent(countMeetComponent)
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