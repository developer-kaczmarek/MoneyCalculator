package kaczmarek.moneycalculator.component.card

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.utils.dpToPx
import kaczmarek.moneycalculator.utils.getString
import kaczmarek.moneycalculator.utils.logError

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
class BanknoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    var count = 0 // Количество купюр одного номинала
        set(value) {
            field = value
            calculateTotalAmount()
        }

    var denomination = 0F  // Номинал купюры
        set(value) {
            field = value
            tvName.text = if (value >= 1) {
                getString(R.string.common_ruble_format, value.toInt())
            } else {
                getString(R.string.common_penny_format, (value * 100).toInt())
            }

            tvType.text = if (value >= 1) {
                getString(R.string.common_bank_ruble)
            } else {
                getString(R.string.common_bank_penny)
            }
        }

    var amount = 0F  // Общая сумма купюр текущего номинала
        private set

    var cardBackgroundColor = "" // Цвет фона карточки
        set(value) {
            field = value
            clContainer.setBackgroundColor(Color.parseColor(field))
        }

    var focusChangeCardListener: OnFocusChangeListener? = null // Слушатель фокуса карточки

    private var clContainer: ConstraintLayout
    private var tvName: TextView
    private var tvType: TextView
    private var etCount: EditText
    private var tvTotalAmount: TextView

    init {
        View.inflate(context, R.layout.layout_banknote_card, this)
        elevation = CARD_ELEVATION_DEFAULT
        radius = context.dpToPx(CARD_RADIUS_DEFAULT)

        clContainer = findViewById(R.id.cl_banknote_container)
        tvName = findViewById(R.id.tv_banknote_name)
        tvType = findViewById(R.id.tv_banknote_type)
        tvTotalAmount = findViewById(R.id.tv_banknote_total_amount)

        etCount = EditText(context).apply {
            id = EDIT_TEXT_ID + View.generateViewId()
            setEms(8)
            gravity = Gravity.CENTER
            hint = getString(R.string.common_count_bank_note)
            filters = arrayOf(InputFilter.LengthFilter(3))
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setHintTextColor(ContextCompat.getColor(context, R.color.white))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
            typeface = ResourcesCompat.getFont(context, R.font.gotham_pro)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
            }
            showSoftInputOnFocus = false
            setOnFocusChangeListener { _, hasFocus ->
                hint = if (hasFocus) "" else getString(R.string.digit_0)
                focusChangeCardListener?.onFocusChange(this@BanknoteCard, hasFocus)
            }
        }

        clContainer.addView(etCount)

        val constraintSet = ConstraintSet()
        with(constraintSet) {
            clone(clContainer)
            connect(etCount.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
            connect(etCount.id, ConstraintSet.TOP, tvType.id, ConstraintSet.TOP, 0)
            connect(etCount.id, ConstraintSet.BOTTOM, tvType.id, ConstraintSet.BOTTOM, 0)
            connect(etCount.id, ConstraintSet.START, tvType.id, ConstraintSet.END, 0)
            applyTo(clContainer)
        }

        context.withStyledAttributes(attrs, R.styleable.BanknoteCard) {
            count = getInteger(R.styleable.BanknoteCard_count, 0)
            denomination = getFloat(R.styleable.BanknoteCard_denomination, 0F)
            amount = getFloat(R.styleable.BanknoteCard_amount, 0F)
        }
    }

    fun setFocusOnCard() {
        etCount.requestFocus()
    }

    /**
     * Метод для добавления знака в поле ввода
     */
    fun addDigit(digit: CharSequence) {
        try {
            if (!(digit == getString(R.string.digit_0) && etCount.text.isEmpty())) {
                etCount.setText(String.format("%s%s", etCount.text, digit))
                etCount.setSelection(etCount.text.length)
                count = etCount.text.toString().toInt()
            }
        } catch (e: Exception) {
            logError(TAG, e.message)
        }
    }

    /**
     * Метод для удаления знака в поле ввода
     */
    fun deleteDigit() {
        try {
            val textFromEditText = etCount.text.toString()
            count = when {
                textFromEditText.isNotEmpty() -> {
                    val currentCount = textFromEditText.substring(0, textFromEditText.length - 1)
                    etCount.setText(currentCount)
                    etCount.setSelection(currentCount.length)
                    if (currentCount.isEmpty()) 0 else currentCount.toInt()
                }
                else -> 0
            }
        } catch (e: Exception) {
            logError(TAG, e.message)
        }
    }

    /**
     * Метод для чистки количества купюр вместе с набором в поле ввода
     */
    fun clearCountWithDigits() {
        try {
            etCount.text.clear()
            count = 0
        } catch (e: Exception) {
            logError(TAG, e.message)
        }
    }

    /**
     * Метод для расчета и вывода суммы банкнот текущего номинала
     */
    private fun calculateTotalAmount() {
        amount = denomination * count
        tvTotalAmount.text = if (denomination >= 1) {
            getString(R.string.common_ruble_format, amount.toInt())
        } else {
            getString(R.string.common_ruble_float_format, amount)
        }
    }

    companion object {
        const val TAG = "BanknoteCard"
        const val EDIT_TEXT_ID = 57900

        private const val CARD_RADIUS_DEFAULT = 8
        private const val CARD_ELEVATION_DEFAULT = 0f
    }
}