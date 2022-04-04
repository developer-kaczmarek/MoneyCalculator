package kaczmarek.moneycalculator.calculator.domain

import kaczmarek.moneycalculator.core.domain.DetailedBanknote
import kaczmarek.moneycalculator.settings.domain.Settings

data class CalculatingSession(
    val banknotes: List<DetailedBanknote>,
    val keyboardLayoutType: Settings.KeyboardLayoutType,
    val isKeepScreenOn: Boolean
)