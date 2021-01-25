package kaczmarek.moneycalculator.ui.base

import android.view.View
import androidx.annotation.IntDef
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.history.TYPE_DATE_ITEM
import kaczmarek.moneycalculator.ui.history.TYPE_SESSION_ITEM
import kaczmarek.moneycalculator.ui.settings.TYPE_SETTING_BANKNOTE_ITEM

const val TYPE_ITEM_PLACEHOLDER = R.layout.rv_placeholder_item

@IntDef(
    TYPE_SETTING_BANKNOTE_ITEM,
    TYPE_DATE_ITEM,
    TYPE_SESSION_ITEM,
    TYPE_ITEM_PLACEHOLDER
)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemViewType

interface ItemBase : DiffComparable {

    @ItemViewType
    val itemViewType: Int
}

data class ItemPlaceholder(override val itemViewType: Int = TYPE_ITEM_PLACEHOLDER) : ItemBase {
    override fun getItemId() = hashCode()
}

class ItemPlaceholderViewHolder(view: View) : BaseViewHolder(view)