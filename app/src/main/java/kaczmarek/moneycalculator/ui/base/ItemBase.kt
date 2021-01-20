package kaczmarek.moneycalculator.ui.base

import androidx.annotation.IntDef
import kaczmarek.moneycalculator.ui.history.TYPE_DATE_ITEM
import kaczmarek.moneycalculator.ui.history.TYPE_SESSION_ITEM
import kaczmarek.moneycalculator.ui.settings.TYPE_SETTING_BANKNOTE_ITEM

@IntDef(
    TYPE_SETTING_BANKNOTE_ITEM,
    TYPE_DATE_ITEM,
    TYPE_SESSION_ITEM
)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemViewType

interface ItemBase : DiffComparable {

    @ItemViewType
    val itemViewType: Int
}