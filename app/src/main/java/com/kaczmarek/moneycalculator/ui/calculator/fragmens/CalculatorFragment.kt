package com.kaczmarek.moneycalculator.ui.calculator.fragmens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.doOnPreDraw
import com.google.android.material.snackbar.Snackbar
import com.kaczmarek.moneycalculator.R
import com.kaczmarek.moneycalculator.ui.base.fragmens.BaseFragment
import com.kaczmarek.moneycalculator.ui.calculator.presenters.CalculatorPresenter
import com.kaczmarek.moneycalculator.ui.calculator.views.CalculatorView
import com.kaczmarek.moneycalculator.ui.main.listeners.BackStackChangeListener
import com.kaczmarek.moneycalculator.utils.BanknoteCard
import com.kaczmarek.moneycalculator.utils.ExternalNavigation
import com.kaczmarek.moneycalculator.utils.FragmentNavigation
import kotlinx.android.synthetic.main.component_banknote_card.view.*
import kotlinx.android.synthetic.main.fragment_calculator.*
import moxy.presenter.InjectPresenter
import kotlin.math.floor

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class CalculatorFragment : BaseFragment(), CalculatorView, FragmentNavigation,
    View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: CalculatorPresenter
    private var navigationListener: ExternalNavigation? = null
    private var focusedEditTextId = 0

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
            (activity as? BackStackChangeListener)?.onBackStackChange(this)
        }
        iv_save.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        iv_next.setOnClickListener(this)
        iv_delete.setOnClickListener(this)
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
    }

    override fun onResume() {
        super.onResume()
        presenter.getBanknotes()
    }

    override fun addBanknoteCard() {
        context?.let { context ->
            presenter.banknotes.forEach { banknote ->
                val componentCard = BanknoteCard(context)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                componentCard.setValueBanknote(banknote.name)
                componentCard.setCount(0)
                componentCard.setColorTheme(banknote.backgroundColor, banknote.textColor)
                presenter.components.add(componentCard)
                componentCard.et_banknote_count.setOnFocusChangeListener { _, hasFocus ->
                    focusedEditTextId = presenter.components.indexOf(componentCard)
                    componentCard.setHideHint(hasFocus)
                    updateStateControlPanel()
                }
                ll_container_components.addView(componentCard, layoutParams)
            }
        }
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
            }
            R.id.iv_save -> {
                presenter.saveSession()
            }
            R.id.b_digit_0_0 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_0_0.text.toString())
            }
            R.id.b_digit_0_1 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_0_1.text.toString())
            }
            R.id.b_digit_0_2 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_0_2.text.toString())
            }
            R.id.b_digit_1_0 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_1_0.text.toString())
            }
            R.id.b_digit_1_1 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_1_1.text.toString())
            }
            R.id.b_digit_1_2 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_1_2.text.toString())
            }
            R.id.b_digit_2_0 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_2_0.text.toString())
            }
            R.id.b_digit_2_1 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_2_1.text.toString())
            }
            R.id.b_digit_2_2 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_2_2.text.toString())
            }
            R.id.b_digit_3_1 -> {
                scrollById(idComponent = focusedEditTextId)
                presenter.components[focusedEditTextId].addDigit(b_digit_3_1.text.toString())
            }
        }

        presenter.banknotes[focusedEditTextId].amount =
            presenter.components[focusedEditTextId].getAmount()
        presenter.banknotes[focusedEditTextId].count = presenter.components[focusedEditTextId].getCount()
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

    private fun scrollById(idComponent: Int) {
        val outLocation = IntArray(2)
        presenter.components[idComponent].getLocationOnScreen(outLocation)
        hsv_calculator.smoothScrollBy(outLocation[0], 0)
        presenter.components[idComponent].et_banknote_count.requestFocus()
        focusedEditTextId = idComponent
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

    companion object {
        const val TAG = "CalculatorFragment"

        fun newInstance(): CalculatorFragment {
            return CalculatorFragment()
        }
    }
}