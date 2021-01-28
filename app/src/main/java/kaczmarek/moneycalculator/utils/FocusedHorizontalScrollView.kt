package kaczmarek.moneycalculator.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView

/**
 * Created by Angelina Podbolotova on 28.09.2019.
 */
class FocusedHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    override fun findFocus(): View {
        return this
    }
}
