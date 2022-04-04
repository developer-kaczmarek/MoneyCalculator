package kaczmarek.moneycalculator.calculator.ui

import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.calculator.domain.CalculatingSession
import kaczmarek.moneycalculator.core.domain.BanknoteId
import kaczmarek.moneycalculator.core.domain.DetailedBanknote
import kaczmarek.moneycalculator.core.ui.utils.toFormattedAmount
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.localizedstring.LocalizedString

data class CalculatingSessionViewData(
    val banknotes: List<DetailedBanknoteViewData>,
    val totalAmount: String,
    val totalCount: Int,
    val isClassicKeyboard: Boolean,
    val isKeepScreenOn: Boolean,
    val isForwardButtonEnabled: Boolean,
    val isNextButtonEnabled: Boolean
) {
    companion object {
        val MOCK = CalculatingSessionViewData(
            banknotes = emptyList(),
            totalCount = 0,
            totalAmount = "0",
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
        LocalizedString.resource(R.string.calculator_name_penny, name)
    } else {
        LocalizedString.resource(R.string.calculator_name_rub, name)
    },
    count = count,
    amount = amount,
    denomination = denomination
)

fun CalculatingSession.toViewData(selectedBanknoteIndex: Int): CalculatingSessionViewData {
    val items = this.banknotes.map { it.toViewData() }
    return CalculatingSessionViewData(
        banknotes = items,
        totalAmount = items.sumOf { item ->
            item.amount.filterNot { it.isWhitespace() }.toDouble()
        }.toFormattedAmount(),
        totalCount = items.sumOf { it.count.toInt() },
        isForwardButtonEnabled = selectedBanknoteIndex != 0,
        isNextButtonEnabled = selectedBanknoteIndex != items.lastIndex,
        isClassicKeyboard = this.keyboardLayoutType == Settings.KeyboardLayoutType.Classic,
        isKeepScreenOn = this.isKeepScreenOn
    )
}