package kaczmarek.moneycalculator.utils

import android.content.Context
import android.graphics.Color
import android.os.Build

import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kaczmarek.moneycalculator.R

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class BanknoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private var isEnable: Boolean
    private var valueBanknote = 0F
    private var count = 0
    private var amount = 0F
    var etCount: EditText
    private var clContainer: ConstraintLayout
    private var tvType: TextView
    private var tvName: TextView
    private var tvTotalAmount: TextView
    var focusCountChangeListener: OnFocusChangeListener? = null

    init {
        View.inflate(context, R.layout.component_banknote_card, this)
        clContainer = findViewById(R.id.cl_banknote_container)
        tvType = findViewById(R.id.tv_banknote_type)
        tvName = findViewById(R.id.tv_banknote_name)
        etCount = findViewById(R.id.et_banknote_count)
        tvTotalAmount = findViewById(R.id.tv_banknote_total_amount)
        isEnable = false
        with(etCount) {
            showSoftInputOnFocus = false
            hint = getString(R.string.common_count_bank_note)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
            }
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
            setOnFocusChangeListener { v, hasFocus ->
                focusCountChangeListener?.onFocusChange(v, hasFocus)
            }
        }
    }

    fun setHideHint(isHide: Boolean) {
        if (isHide) {
            etCount.hint = ""
        } else {
            etCount.setHint(R.string.digit_0)
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

    fun addDigit(digit: CharSequence) {
        if (!(digit == getString(R.string.digit_0) && etCount.text.isEmpty())) {
            etCount.setText(
                String.format(
                    "%s%s",
                    etCount.text,
                    digit
                )
            )
            etCount.setSelection(etCount.text.length)
            setCount(etCount.text.toString().toInt())
        }
    }

    fun setFocus() {
        etCount.requestFocus()
    }

    fun getAmount() = amount

    fun deleteDigit() {
        val textFromEditText = etCount.text.toString()
        if (textFromEditText.isNotEmpty()) {
            etCount.setText(
                textFromEditText.substring(
                    0,
                    textFromEditText.length - 1
                )
            )
            etCount.setSelection(etCount.text.length)
        }
        setCount(
            if (etCount.text.isNotEmpty()) {
                etCount.text.toString().toInt()
            } else {
                0
            }
        )
    }

    private fun setNameBanknote(value: Float) {
        if (value >= 1) {
            tvName.text = getString(R.string.common_ruble_format, value.toInt())
            tvType.setText(R.string.common_bank_ruble)
        } else {
            tvName.text = getString(R.string.common_penny_format, (value * 100).toInt())
            tvType.setText(R.string.common_bank_penny)
        }
    }

    private fun calculateTotalAmount() {
        amount = valueBanknote * count
        if (valueBanknote >= 1) {
            tvTotalAmount.text =
                getString(R.string.common_ruble_format, amount.toInt())
        } else {
            tvTotalAmount.text = getString(R.string.common_ruble_float_format, amount)
        }
    }

    fun setColorTheme(colorBackground: String) {
        clContainer.setBackgroundColor(Color.parseColor(colorBackground))
    }
}