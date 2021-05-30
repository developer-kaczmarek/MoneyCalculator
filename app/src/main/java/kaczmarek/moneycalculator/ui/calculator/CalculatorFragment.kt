package kaczmarek.moneycalculator.ui.calculator

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.BaseFragment
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.MainActivity
import kaczmarek.moneycalculator.component.card.BanknoteCard
import kaczmarek.moneycalculator.component.keyboard.Keyboard
import kaczmarek.moneycalculator.utils.dpToPx
import kaczmarek.moneycalculator.utils.gone
import kaczmarek.moneycalculator.utils.logDebug
import kaczmarek.moneycalculator.utils.visible
import moxy.ktx.moxyPresenter

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */

interface CalculatorView : ViewBase {
    fun showBanknoteCards()
    fun setTotalAmount(stringAmount: String)
}

class CalculatorFragment : BaseFragment(R.layout.fragment_calculator), CalculatorView,
    View.OnClickListener, Keyboard.OnClickKeyboardListener, View.OnFocusChangeListener {

    private var focusableBanknoteCardPosition = -1
    private var countMeetComponent = 0
    private lateinit var ivCalculatorBack: ImageView
    private lateinit var ivCalculatorNext: ImageView
    private lateinit var llCalculatorBanknotesContainer: LinearLayout
    private lateinit var tvCalculatorTotalAmount: TextView
    private lateinit var hsvCalculator: HorizontalScrollView
    private lateinit var kbCalculator: Keyboard

    private val presenter by moxyPresenter { CalculatorPresenter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivCalculatorBack = view.findViewById(R.id.iv_calculator_back)
        ivCalculatorNext = view.findViewById(R.id.iv_calculator_next)
        kbCalculator = view.findViewById(R.id.kb_calculator)
        llCalculatorBanknotesContainer = view.findViewById(R.id.ll_calculator_banknotes_container)
        tvCalculatorTotalAmount = view.findViewById(R.id.tv_calculator_total_amount)
        val llCalculatorControlPanel = view.findViewById<LinearLayout>(R.id.ll_calculator_control_panel)
        hsvCalculator = view.findViewById(R.id.hsv_calculator)
        ivCalculatorBack.setOnClickListener(this)
        ivCalculatorNext.setOnClickListener(this)

        with(kbCalculator) {
            selectKeyboardType(presenter.getKeyboardLayout())
            setOnClickKeyboardListener(this@CalculatorFragment)
        }
        showBanknoteCards()
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

    /**
     * Метод для добавления карточек банктнот в разметку,
     * исходя из видимости банкнот
     */
    override fun showBanknoteCards() {
        context?.let { context ->

            /*//llCalculatorBanknotesContainer.removeAllViews()
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val horizontal = context.dpToPx(16).toInt()
            val vertical = context.dpToPx(24).toInt()
            params.setMargins(horizontal, vertical, horizontal, vertical)*/

            presenter.banknotes.forEachIndexed { index, item ->
                /*val banknoteCard = BanknoteCard(context).apply {
                    id = index
                    denomination = item.name
                    addDigit(item.count.toString())
                    cardBackgroundColor = item.backgroundColor
                    focusChangeCardListener = this@CalculatorFragment
                }
                llCalculatorBanknotesContainer.addView(banknoteCard, params)*/
                val view = llCalculatorBanknotesContainer[index] as BanknoteCard
                if (view.denomination == item.name) {
                    view.isVisible = item.isShow
                    if (item.isShow) {
                        view.addDigit(item.count.toString())
                        view.focusChangeCardListener = this@CalculatorFragment
                    }
                }
            }
            (llCalculatorBanknotesContainer[0] as BanknoteCard).setFocusOnCard()
        }
        updateStateNavigationButtons()
    }

    /**
     * Метод для обновления значения итоговой суммы в интерфейсе
     */
    override fun setTotalAmount(stringAmount: String) {
        tvCalculatorTotalAmount.text = stringAmount
    }

    /**
     * Метод для обработки нажатия на View
     */
    override fun onClick(view: View) {
        val position = focusableBanknoteCardPosition
        if (position == -1) return
        val card = llCalculatorBanknotesContainer.getChildAt(focusableBanknoteCardPosition)
        if (card is BanknoteCard) {
            when (view.id) {
                R.id.iv_calculator_back -> {
                    if (position != 0) {
                        val newPosition = position - 1
                        (llCalculatorBanknotesContainer[newPosition] as BanknoteCard).setFocusOnCard()
                    }
                }
                R.id.iv_calculator_next -> {
                    if (position != presenter.banknotes.size - 1) {
                        val newPosition = position + 1
                        (llCalculatorBanknotesContainer[newPosition] as BanknoteCard).setFocusOnCard()
                    }
                }
            }
        }
    }

    override fun onNumberClick(number: CharSequence) {
        val position = focusableBanknoteCardPosition
        if (position == -1) return
        val card = llCalculatorBanknotesContainer.getChildAt(focusableBanknoteCardPosition)
        if (card is BanknoteCard) {
            card.addDigit(number)
            presenter.updateCountAndAmountBanknote(position, card.count, card.amount)
            presenter.updateTotalAmount()
            scrollToFocusBanknoteCard(card)
        }
    }

    override fun onDeleteClick(isLongClick: Boolean) {
        if (isLongClick) {
            for (position in 0 until llCalculatorBanknotesContainer.childCount) {
                val view = llCalculatorBanknotesContainer[position]
                if (view is BanknoteCard) {
                    view.clearCountWithDigits()
                    presenter.updateCountAndAmountBanknote(position, 0, 0F)
                }
            }
            presenter.updateTotalAmount()
            val firstCard = llCalculatorBanknotesContainer[0] as BanknoteCard
            firstCard.setFocusOnCard()
            scrollToFocusBanknoteCard(firstCard)
            showMessage(getString(R.string.fragment_calculator_all_delete_values))
        } else {
            val position = focusableBanknoteCardPosition
            if (position == -1) return
            val card = llCalculatorBanknotesContainer.getChildAt(focusableBanknoteCardPosition)
            if (card is BanknoteCard) {
                card.deleteDigit()
                presenter.updateCountAndAmountBanknote(position, card.count, card.amount)
                presenter.updateTotalAmount()
                scrollToFocusBanknoteCard(card)
            }
        }
    }

    override fun onSaveClick() {
        presenter.saveCurrentCalculatingSession()
    }

    /**
     * Метод для обработки установленного фокуса на элемент.
     * Если v является BanknoteCard, то мы записываем позицию элемента в фокусе
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus && v is BanknoteCard) {
            focusableBanknoteCardPosition = llCalculatorBanknotesContainer.indexOfChild(v)
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
        hsvCalculator.smoothScrollBy(outLocation[0] - startMargin, 0)
    }

    /**
     * Метод для обновления состояния навигационных кнопок (стрелок вперед - назад)
     */
    private fun updateStateNavigationButtons() {
        ivCalculatorBack.isEnabled =
            presenter.banknotes.size == 1 || focusableBanknoteCardPosition != 0
        ivCalculatorNext.isEnabled =
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
        /* when (countMeetComponent) {
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
         }*/
    }

    companion object {
        const val TAG = "CalculatorFragment"
    }
}