package com.kaczmarek.moneycalculator.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.kaczmarek.moneycalculator.R
import kotlinx.android.synthetic.main.component_banknote_card.view.*
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class BanknoteCard : LinearLayout {
    private var isEnable: Boolean
    private var valueBanknote = 0F
    private var count = 0
    private var amount = 0F
    var editTextBanknoteCount: EditText


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        View.inflate(context, R.layout.component_banknote_card, this)
        isEnable = false
        editTextBanknoteCount = EditText(context)
        editTextBanknoteCount
        editTextBanknoteCount.id = View.generateViewId()
        editTextBanknoteCount.setEms(8)
        editTextBanknoteCount.gravity = Gravity.CENTER
        editTextBanknoteCount.hint = getString(R.string.common_count_bank_note)
        editTextBanknoteCount.filters = arrayOf(InputFilter.LengthFilter(3))
        editTextBanknoteCount.setTextColor(ContextCompat.getColor(context, R.color.white))
        editTextBanknoteCount.setHintTextColor(ContextCompat.getColor(context, R.color.white))
        editTextBanknoteCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        editTextBanknoteCount.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.white)
        editTextBanknoteCount.typeface = ResourcesCompat.getFont(context, R.font.gotham_pro)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editTextBanknoteCount.importantForAutofill =
                View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }
        editTextBanknoteCount.showSoftInputOnFocus = false
        val params = LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1.0f
            gravity = Gravity.CENTER
        }
        params.setMargins(0, 0, dpToPxInt(16), 0)
        editTextBanknoteCount.layoutParams = params
        ll_container_count.addView(editTextBanknoteCount)
    }

    fun setHideHint(isHide: Boolean) {
        if (isHide) {
            editTextBanknoteCount.hint = ""
        } else {
            editTextBanknoteCount.setHint(R.string.digit_0)
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
        if (!(digit == getString(R.string.digit_0) && editTextBanknoteCount.text.isEmpty())) {
            editTextBanknoteCount.setText(
                String.format(
                    "%s%s",
                    editTextBanknoteCount.text,
                    digit
                )
            )
            editTextBanknoteCount.setSelection(editTextBanknoteCount.text.length)
            setCount(editTextBanknoteCount.text.toString().toInt())
        }
    }

    fun getAmount() = amount

    fun deleteDigit() {
        val textFromEditText = editTextBanknoteCount.text.toString()
        if (textFromEditText.isNotEmpty()) {
            editTextBanknoteCount.setText(
                textFromEditText.substring(
                    0,
                    textFromEditText.length - 1
                )
            )
            editTextBanknoteCount.setSelection(editTextBanknoteCount.text.length)
        }
        setCount(
            if (editTextBanknoteCount.text.isNotEmpty()) {
                editTextBanknoteCount.text.toString().toInt()
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