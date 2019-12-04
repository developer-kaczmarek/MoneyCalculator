package kaczmarek.moneycalculator.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView

/**
 * Created by Angelina Podbolotova on 28.09.2019.
 */
class CustomScrollView : HorizontalScrollView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun findFocus(): View {
        return this
    }
}
