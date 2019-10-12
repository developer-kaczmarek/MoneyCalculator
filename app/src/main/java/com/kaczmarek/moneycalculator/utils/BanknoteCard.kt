package com.kaczmarek.moneycalculator.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.kaczmarek.moneycalculator.R
import com.ub.utils.UbUtils.getString
import kotlinx.android.synthetic.main.component_banknote_card.view.*

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class BanknoteCard : LinearLayout {
    private var isEnable: Boolean
    private var valueBanknote = 0F
    private var count = 0
    private var amount = 0F


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        View.inflate(context, R.layout.component_banknote_card, this)
        isEnable = false
        et_banknote_count.showSoftInputOnFocus = false

        et_banknote_count.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                et_banknote_count.setHint(R.string.digit_0)
            } else {
                et_banknote_count.hint = ""
            }
        }
    }

    fun setHideHint(isHide: Boolean){
        if(isHide){
            et_banknote_count.hint = ""
        } else {
            et_banknote_count.setHint(R.string.digit_0)
        }
    }

    fun setValueBanknote(value: Float) {
        valueBanknote = value
        setNameBanknote(valueBanknote)
    }

    fun setCount(count: Int = 0) {
        this.count = count
        calculateTotalAmount()
    }

    fun getCount() = count

    fun addDigit(digit: String) {
        if (!(digit == getString(R.string.digit_0) && et_banknote_count.text.isEmpty())){
            et_banknote_count.setText(
                String.format(
                    "%s%s",
                    et_banknote_count.text,
                    digit
                )
            )
            et_banknote_count.setSelection(et_banknote_count.text.length)
            setCount(et_banknote_count.text.toString().toInt())
        }
    }

    fun getAmount() = amount

    fun deleteDigit() {
        val textFromEditText = et_banknote_count.text.toString()
        et_banknote_count.setText(textFromEditText.substring(0, textFromEditText.length - 1))
        et_banknote_count.setSelection(et_banknote_count.text.length)
        setCount(
            if (et_banknote_count.text.isNotEmpty()) {
                et_banknote_count.text.toString().toInt()
            } else {
                0
            }
        )
    }

    private fun setNameBanknote(value: Float) {
        if (value >= 1) {
            tv_banknote_name.text = getString(R.string.common_ruble_format, value.toInt())
            tv_banknote_type.setText(R.string.common_bank_ruble)
        } else {
            tv_banknote_name.text = getString(R.string.common_penny_format, (value * 100).toInt())
            tv_banknote_type.setText(R.string.common_bank_penny)
        }
    }

    private fun calculateTotalAmount() {
        amount = valueBanknote * count

        if (valueBanknote >= 1) {
            tv_banknote_total_amount.text =
                getString(R.string.common_ruble_format, amount.toInt())
        } else {
            tv_banknote_total_amount.text = getString(R.string.common_ruble_float_format, amount)
        }
    }

    fun setColorTheme(colorBackground: String, colorTextType: String) {
        ll_banknote_info.setBackgroundColor(Color.parseColor(colorBackground))
        tv_banknote_type.setTextColor(Color.parseColor(colorTextType))
    }
}