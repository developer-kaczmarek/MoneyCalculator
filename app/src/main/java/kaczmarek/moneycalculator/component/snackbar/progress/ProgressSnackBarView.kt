package kaczmarek.moneycalculator.component.snackbar.progress

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.utils.getString

class ProgressSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val tvTime: TextView
    private val pbTime: ProgressBar
    private val btnAction: Button

    init {
        View.inflate(context, R.layout.layout_progress_snack_bar, this)
        tvTime = findViewById(R.id.tv_progress_snack_bar_time)
        pbTime = findViewById(R.id.pb_progress_snack_bar)
        btnAction = findViewById(R.id.btn_progress_snack_bar_action)
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}

    fun startFiveSecondsProgress(action: () -> Unit = {}) {
        pbTime.max = 100
        btnAction.setOnClickListener {
            action()
        }
        object : CountDownTimer(5000L, 50L) {
            var procent = 100

            override fun onTick(millisUntilFinished: Long) {
                pbTime.progress = procent
                if (procent % 20.0 == 0.0) tvTime.text = (procent / 20).toString()
                procent--
            }

            override fun onFinish() {
                pbTime.progress = 0
                tvTime.text = getString(R.string.digit_0)
            }
        }.start()
    }
}