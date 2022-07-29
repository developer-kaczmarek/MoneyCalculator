package kaczmarek.moneycalculator.calculator.ui

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.calculator.domain.CalculatingSession
import kaczmarek.moneycalculator.core.banknote.domain.BanknoteId
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.core.utils.toFormattedAmount
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.localizedstring.LocalizedString

data class CalculatingSessionViewData(
    val banknotes: List<DetailedBanknoteViewData>,
    val totalAmount: LocalizedString,
    val totalCount: LocalizedString,
    val isClassicKeyboard: Boolean,
    val isKeepScreenOn: Boolean,
    val isForwardButtonEnabled: Boolean,
    val isNextButtonEnabled: Boolean
) {
    companion object {
        val MOCK = CalculatingSessionViewData(
            banknotes = emptyList(),
            totalCount = LocalizedString.raw("0 шт"),
            totalAmount = LocalizedString.raw(" 0 руб"),
            isClassicKeyboard = true,
            isForwardButtonEnabled = false,
            isNextButtonEnabled = false,
            isKeepScreenOn = false
        )
    }
}

data class DetailedBanknoteViewData(
    val id: BanknoteId,
    val borderColor: Long,
    val name: LocalizedString,
    val count: String,
    val amount: String,
    val denomination: Double
)

fun DetailedBanknote.toViewData() = DetailedBanknoteViewData(
    id = id,
    borderColor = backgroundColor,
    name = if (denomination < 1) {
        LocalizedString.resource(R.string.common_name_penny, name)
    } else {
        LocalizedString.resource(R.string.common_name_rub, name)
    },
    count = count,
    amount = amount,
    denomination = denomination
)

fun CalculatingSession.toViewData(selectedBanknoteIndex: Int): CalculatingSessionViewData {
    val items = this.banknotes.map { it.toViewData() }
    return CalculatingSessionViewData(
        banknotes = items,
        totalAmount = LocalizedString.resource(
            R.string.calculator_total_amount,
            items.sumOf { item ->
                item.amount.filterNot { it.isWhitespace() }.toDouble()
            }.toFormattedAmount()
        ),
        totalCount = LocalizedString.resource(
            R.string.calculator_total_count,
            items.sumOf { it.count.toInt() }),
        isForwardButtonEnabled = selectedBanknoteIndex != 0,
        isNextButtonEnabled = selectedBanknoteIndex != items.lastIndex,
        isClassicKeyboard = this.keyboardLayoutType == Settings.KeyboardLayoutType.Classic,
        isKeepScreenOn = this.isKeepScreenOn
    )
}