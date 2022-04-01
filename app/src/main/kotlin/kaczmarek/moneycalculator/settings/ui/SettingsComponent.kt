package kaczmarek.moneycalculator.settings.ui

import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.loading.simple.Loading

interface SettingsComponent {

    val settingsViewState: Loading.State<SettingsViewData>

    sealed interface Output {
        object ThemeChanged : Output
    }

    fun onBanknoteClick(banknote: Banknote)

    fun onKeyboardLayoutTypeClick(keyboardLayoutType: Settings.KeyboardLayoutType)

    fun onHistoryStoragePeriodClick(historyStoragePeriod: Settings.HistoryStoragePeriod)

    fun onContactDeveloperClick()

    fun onPrivacyPolicyClick()

    fun onGithubClick()

    fun onGooglePlayClick()

    fun onDisplayAlwaysOnClick(oldCheckedValue: Boolean)

    fun onThemeTypeClick(themeType: Settings.ThemeType)
}