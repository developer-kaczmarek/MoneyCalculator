package kaczmarek.moneycalculator.component.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import kaczmarek.moneycalculator.R

class Keyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener, View.OnLongClickListener {

    private var bDigit00: Button
    private var bDigit01: Button
    private var bDigit02: Button
    private var bDigit10: Button
    private var bDigit11: Button
    private var bDigit12: Button
    private var bDigit20: Button
    private var bDigit21: Button
    private var bDigit22: Button
    private var bDigit31: Button
    private var ivSave: ImageView
    private var ivDelete: ImageView
    private var onClickKeyboardListener: OnClickKeyboardListener? = null

    init {
        View.inflate(context, R.layout.layout_keyboard, this)
        bDigit00 = findViewById(R.id.b_keyboard_digit_0_0)
        bDigit01 = findViewById(R.id.b_keyboard_digit_0_1)
        bDigit02 = findViewById(R.id.b_keyboard_digit_0_2)
        bDigit10 = findViewById(R.id.b_keyboard_digit_1_0)
        bDigit11 = findViewById(R.id.b_keyboard_digit_1_1)
        bDigit12 = findViewById(R.id.b_keyboard_digit_1_2)
        bDigit20 = findViewById(R.id.b_keyboard_digit_2_0)
        bDigit21 = findViewById(R.id.b_keyboard_digit_2_1)
        bDigit22 = findViewById(R.id.b_keyboard_digit_2_2)
        bDigit31 = findViewById(R.id.b_keyboard_digit_3_1)
        ivSave = findViewById(R.id.iv_keyboard_save)
        ivDelete = findViewById(R.id.iv_keyboard_delete)

        bDigit00.setOnClickListener(this)
        bDigit01.setOnClickListener(this)
        bDigit02.setOnClickListener(this)
        bDigit10.setOnClickListener(this)
        bDigit11.setOnClickListener(this)
        bDigit12.setOnClickListener(this)
        bDigit20.setOnClickListener(this)
        bDigit21.setOnClickListener(this)
        bDigit22.setOnClickListener(this)
        bDigit31.setOnClickListener(this)
        ivSave.setOnClickListener(this)
        with(ivDelete) {
            setOnClickListener(this@Keyboard)
            setOnLongClickListener(this@Keyboard)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_keyboard_delete -> onClickKeyboardListener?.onDeleteClick(isLongClick = false)
            R.id.iv_keyboard_save -> onClickKeyboardListener?.onSaveClick()
            else -> onClickKeyboardListener?.onNumberClick((v as Button).text)
        }
    }

    override fun onLongClick(v: View): Boolean {
        return if (v.id == R.id.iv_keyboard_delete) {
            onClickKeyboardListener?.onDeleteClick(true)
            true
        } else {
            false
        }
    }

    fun selectKeyboardType(@KeyboardType type: Int) {
        bDigit00.setText(if (type == NUMPAD) R.string.digit_7 else R.string.digit_1)
        bDigit01.setText(if (type == NUMPAD) R.string.digit_8 else R.string.digit_2)
        bDigit02.setText(if (type == NUMPAD) R.string.digit_9 else R.string.digit_3)
        bDigit20.setText(if (type == NUMPAD) R.string.digit_1 else R.string.digit_7)
        bDigit21.setText(if (type == NUMPAD) R.string.digit_2 else R.string.digit_8)
        bDigit22.setText(if (type == NUMPAD) R.string.digit_3 else R.string.digit_9)
    }

    fun setOnClickKeyboardListener(listener: OnClickKeyboardListener?) {
        onClickKeyboardListener = listener
    }

    companion object {
        const val CLASSIC = 0
        const val NUMPAD = 1

        @IntDef(
            CLASSIC,
            NUMPAD
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class KeyboardType
    }

    interface OnClickKeyboardListener {
        fun onNumberClick(number: CharSequence)
        fun onDeleteClick(isLongClick: Boolean)
        fun onSaveClick()
    }
}