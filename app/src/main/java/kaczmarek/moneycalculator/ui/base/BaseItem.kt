package kaczmarek.moneycalculator.ui.base

import android.view.View
import androidx.annotation.IntDef
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.history.TYPE_HISTORY_DATE_ITEM
import kaczmarek.moneycalculator.ui.history.TYPE_HISTORY_SESSION_DETAILS_ITEM
import kaczmarek.moneycalculator.ui.history.TYPE_HISTORY_SESSION_ITEM
import kaczmarek.moneycalculator.ui.settings.TYPE_SETTING_BANKNOTE_ITEM

const val TYPE_PLACEHOLDER_ITEM = R.layout.rv_placeholder_item

@IntDef(
    TYPE_SETTING_BANKNOTE_ITEM,
    TYPE_HISTORY_DATE_ITEM,
    TYPE_HISTORY_SESSION_ITEM,
    TYPE_PLACEHOLDER_ITEM,
    TYPE_HISTORY_SESSION_DETAILS_ITEM
)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemViewType

class ItemPlaceholder(override val itemViewType: Int = TYPE_PLACEHOLDER_ITEM) : BaseItem {
    override fun getItemId() = hashCode()
}

class ItemPlaceholderViewHolder(view: View) : BaseViewHolder(view)

interface BaseItem : DiffComparable {

    @ItemViewType
    val itemViewType: Int
}