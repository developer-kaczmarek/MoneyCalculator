package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.core.banknote.domain.Banknote

data class Settings(
    val banknotes: List<Banknote>,
    val historyStoragePeriod: HistoryStoragePeriod,
    val keyboardLayoutType: KeyboardLayoutType,
    val isKeepScreenOn: Boolean,
    val themeType: ThemeType
) {
    enum class HistoryStoragePeriod(val id: Int) {
        Indefinitely(0),
        FourteenDays(1),
        ThirtyDays(2)
    }

    enum class KeyboardLayoutType(val id: Int) {
        Classic(0),
        NumPad(1)
    }

    enum class ThemeType {
        Light,
        Dark,
        System
    }
}