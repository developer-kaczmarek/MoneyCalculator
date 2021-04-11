package kaczmarek.moneycalculator.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.LinearInterpolator
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
import kaczmarek.moneycalculator.utils.dpToPx
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

interface CalculatorView : ViewBase {
    fun showBanknoteCards()
    fun setTotalAmount(stringAmount: String)
}

class CalculatorFragment : BaseFragment(R.layout.fragment_calculator), CalculatorView,
    View.OnClickListener, View.OnLongClickListener, View.OnFocusChangeListener {

    private var focusableBanknoteCardPosition = -1
    private var countMeetComponent = 0
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter { CalculatorPresenter() }

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
        binding.ivCalculatorSave.setOnClickListener(this)
        binding.ivCalculatorBack.setOnClickListener(this)
        binding.ivCalculatorNext.setOnClickListener(this)

        binding.bCalculatorDigit00.setOnClickListener(this)
        binding.bCalculatorDigit01.setOnClickListener(this)
        binding.bCalculatorDigit02.setOnClickListener(this)
        binding.bCalculatorDigit10.setOnClickListener(this)
        binding.bCalculatorDigit11.setOnClickListener(this)
        binding.bCalculatorDigit12.setOnClickListener(this)
        binding.bCalculatorDigit20.setOnClickListener(this)
        binding.bCalculatorDigit21.setOnClickListener(this)
        binding.bCalculatorDigit22.setOnClickListener(this)
        binding.bCalculatorDigit31.setOnClickListener(this)

        with(binding.ivCalculatorDelete) {
            setOnClickListener(this@CalculatorFragment)
            setOnLongClickListener(this@CalculatorFragment)
        }

        if (presenter.getKeyboardLayout() == NUMPAD) {
            binding.bCalculatorDigit00.setText(R.string.digit_7)
            binding.bCalculatorDigit01.setText(R.string.digit_8)
            binding.bCalculatorDigit02.setText(R.string.digit_9)
            binding.bCalculatorDigit20.setText(R.string.digit_1)
            binding.bCalculatorDigit21.setText(R.string.digit_2)
            binding.bCalculatorDigit22.setText(R.string.digit_3)
        }

        presenter.getVisibleBanknotes()

        if (presenter.getCountKnownComponents() <= 2) {
            countMeetComponent = presenter.getCountKnownComponents()
            startAcquaintanceWithComponent(view)
        }
    }

    override fun onStart() {
        super.onStart()
        val flags = activity?.window?.attributes?.flags
        if (presenter.isAlwaysBacklightOn() && flags?.and((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)) == 0) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onPause() {
        presenter.saveBanknotesFromCurrentSession()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Метод для добавления карточек банктнот в разметку,
     * исходя из видимости банкнот
     */
    override fun showBanknoteCards() {
        context?.let { context ->
            binding.llCalculatorBanknotesContainer.removeAllViews()
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val horizontal = context.dpToPx(16).toInt()
            val vertical = context.dpToPx(24).toInt()
            params.setMargins(horizontal, vertical, horizontal, vertical)

            presenter.banknotes.forEachIndexed { index, item ->
                val banknoteCard = BanknoteCard(context).apply {
                    id = index
                    denomination = item.name
                    addDigit(item.count.toString())
                    cardBackgroundColor = item.backgroundColor
                    focusChangeCardListener = this@CalculatorFragment
                }
                binding.llCalculatorBanknotesContainer.addView(banknoteCard, params)
            }
            (binding.llCalculatorBanknotesContainer[0] as BanknoteCard).setFocusOnCard()
        }
        updateStateNavigationButtons()
        binding.clCalculatorContainer.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            start()
        }
    }

    /**
     * Метод для обновления значения итоговой суммы в интерфейсе
     */
    override fun setTotalAmount(stringAmount: String) {
        binding.tvCalculatorTotalAmount.text = stringAmount
    }

    /**
     * Метод для обработки долго нажатия,
     * в основном нацелен на обработку кнопки удаления значений
     */
    override fun onLongClick(v: View): Boolean {
        return if (v.id == R.id.iv_calculator_delete) {
            for (position in 0 until binding.llCalculatorBanknotesContainer.childCount) {
                val view = binding.llCalculatorBanknotesContainer[position]
                if (view is BanknoteCard) {
                    view.clearCountWithDigits()
                    presenter.updateCountAndAmountBanknote(position, 0, 0F)
                }
            }
            presenter.updateTotalAmount()
            val firstCard = binding.llCalculatorBanknotesContainer[0] as BanknoteCard
            firstCard.setFocusOnCard()
            scrollToFocusBanknoteCard(firstCard)
            showMessage(getString(R.string.fragment_calculator_all_delete_values))
            true
        } else {
            false
        }
    }

    /**
     * Метод для обработки нажатия на View
     */
    override fun onClick(view: View) {
        val position = focusableBanknoteCardPosition
        if (position == -1) return
        val card = binding.llCalculatorBanknotesContainer.getChildAt(focusableBanknoteCardPosition)
        if (card is BanknoteCard) {
            when (view.id) {
                R.id.iv_calculator_back -> {
                    if (position != 0) {
                        val newPosition = position - 1
                        (binding.llCalculatorBanknotesContainer[newPosition] as BanknoteCard).setFocusOnCard()
                    }
                }
                R.id.iv_calculator_next -> {
                    if (position != presenter.banknotes.size - 1) {
                        val newPosition = position + 1
                        (binding.llCalculatorBanknotesContainer[newPosition] as BanknoteCard).setFocusOnCard()
                    }
                }
                R.id.iv_calculator_delete -> {
                    card.deleteDigit()
                    presenter.updateCountAndAmountBanknote(position, card.count, card.amount)
                    presenter.updateTotalAmount()
                    scrollToFocusBanknoteCard(card)
                }
                R.id.iv_calculator_save -> presenter.saveCurrentCalculatingSession()
                else -> {
                    card.addDigit((view as Button).text)
                    presenter.updateCountAndAmountBanknote(position, card.count, card.amount)
                    presenter.updateTotalAmount()
                    scrollToFocusBanknoteCard(card)
                }
            }
        }
    }

    /**
     * Метод для обработки установленного фокуса на элемент.
     * Если v является BanknoteCard, то мы записываем позицию элемента в фокусе
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus && v is BanknoteCard) {
            focusableBanknoteCardPosition = binding.llCalculatorBanknotesContainer.indexOfChild(v)
            updateStateNavigationButtons()
            scrollToFocusBanknoteCard(v)
        }
    }

    /**
     * Метод для скролла до карточки банкноты.
     * @param card Компонент BanknoteCard, который должен оказаться
     * в поле зрения. Как правило, на которой был установлен фокус в последний раз
     */
    private fun scrollToFocusBanknoteCard(card: BanknoteCard) {
        val outLocation = IntArray(2)
        card.getLocationOnScreen(outLocation)
        val startMargin = context?.dpToPx(8)?.toInt() ?: 0
        binding.hsvCalculator.smoothScrollBy(outLocation[0] - startMargin, 0)
    }

    /**
     * Метод для обновления состояния навигационных кнопок (стрелок вперед - назад)
     */
    private fun updateStateNavigationButtons() {
        binding.ivCalculatorBack.isEnabled =
            presenter.banknotes.size == 1 || focusableBanknoteCardPosition != 0
        binding.ivCalculatorNext.isEnabled =
            presenter.banknotes.size == 1 || focusableBanknoteCardPosition != presenter.banknotes.size - 1
    }

    /**
     * Метод вызывается для знакомства с интерфейсом приложения
     */
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

    /**
     * Метод вызывается для знакомства с интерфейсом приложения
     */
    private fun startAcquaintanceWithComponent(view: View) {
        when (countMeetComponent) {
            0 -> meetAppOnCalculator(
                view,
                R.id.ll_calculator_control_panel,
                getString(R.string.fragment_calculator_title_component_board),
                getString(R.string.fragment_calculator_description_component_board),
                150
            )
            1 -> {
                meetAppOnCalculator(
                    view,
                    R.id.iv_calculator_delete,
                    getString(R.string.fragment_calculator_title_component_delete),
                    getString(R.string.fragment_calculator_description_component_delete),
                    40
                )
            }
            2 -> {
                meetAppOnCalculator(
                    view,
                    R.id.iv_calculator_save,
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