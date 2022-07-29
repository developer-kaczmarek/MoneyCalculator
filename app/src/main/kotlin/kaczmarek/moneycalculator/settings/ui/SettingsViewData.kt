package kaczmarek.moneycalculator.settings.ui

import kaczmarek.moneycalculator.BuildConfig
import kaczmarek.moneycalculator.core.banknote.domain.Banknote
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.localizedstring.LocalizedString
import kaczmarek.moneycalculator.R

data class SettingsViewData(
    val banknotes: List<SettingsChoice<Banknote>>,
    val historyStoragePeriods: List<SettingsChoice<Settings.HistoryStoragePeriod>>,
    val keyboardLayoutTypes: List<SettingsChoice<Settings.KeyboardLayoutType>>,
    val isKeepScreenOn: Boolean,
    val themeTypes: List<SettingsChoice<Settings.ThemeType>>,
    val versionDescription: LocalizedString
) {

    companion object {
        val MOCK = SettingsViewData(
            banknotes = emptyList(),
            historyStoragePeriods = listOf(
                SettingsChoice(
                    id = Settings.HistoryStoragePeriod.Indefinitely.name,
                    title = LocalizedString.resource(R.string.settings_indefinitely),
                    isChosen = false,
                    output = Settings.HistoryStoragePeriod.Indefinitely
                ),
                SettingsChoice(
                    id = Settings.HistoryStoragePeriod.FourteenDays.name,
                    title = LocalizedString.resource(R.string.settings_in_fourteen_days),
                    isChosen = true,
                    output = Settings.HistoryStoragePeriod.FourteenDays
                ),
                SettingsChoice(
                    id = Settings.HistoryStoragePeriod.ThirtyDays.name,
                    title = LocalizedString.resource(R.string.settings_in_thirty_days),
                    isChosen = false,
                    output = Settings.HistoryStoragePeriod.ThirtyDays
                )
            ),
            keyboardLayoutTypes = listOf(
                SettingsChoice(
                    id = Settings.KeyboardLayoutType.Classic.name,
                    title = LocalizedString.resource(R.string.settings_phone_keyboard),
                    isChosen = false,
                    output = Settings.KeyboardLayoutType.Classic
                ),
                SettingsChoice(
                    id = Settings.KeyboardLayoutType.NumPad.name,
                    title = LocalizedString.resource(R.string.settings_numpad_keyboard),
                    isChosen = true,
                    output = Settings.KeyboardLayoutType.NumPad
                )
            ),
            isKeepScreenOn = false,
            themeTypes = listOf(
                SettingsChoice(
                    id = Settings.ThemeType.Light.name,
                    title = LocalizedString.resource(R.string.settings_light_theme),
                    isChosen = true,
                    output = Settings.ThemeType.Light
                ),
                SettingsChoice(
                    id = Settings.ThemeType.Dark.name,
                    title = LocalizedString.resource(R.string.settings_dark_theme),
                    isChosen = false,
                    output = Settings.ThemeType.Dark
                ),
                SettingsChoice(
                    id = Settings.ThemeType.System.name,
                    title = LocalizedString.resource(R.string.settings_system_theme),
                    isChosen = false,
                    output = Settings.ThemeType.System
                )
            ),
            versionDescription = LocalizedString.resource(
                R.string.settings_version,
                BuildConfig.VERSION_NAME
            )
        )
    }
}

fun Settings.toViewData(): SettingsViewData {
    return SettingsViewData(
        banknotes = banknotes.map { it.toSettingsChoice() },
        historyStoragePeriods = listOf(
            SettingsChoice(
                id = Settings.HistoryStoragePeriod.Indefinitely.name,
                title = LocalizedString.resource(R.string.settings_indefinitely),
                isChosen = historyStoragePeriod == Settings.HistoryStoragePeriod.Indefinitely,
                output = Settings.HistoryStoragePeriod.Indefinitely
            ),
            SettingsChoice(
                id = Settings.HistoryStoragePeriod.FourteenDays.name,
                title = LocalizedString.resource(R.string.settings_in_fourteen_days),
                isChosen = historyStoragePeriod == Settings.HistoryStoragePeriod.FourteenDays,
                output = Settings.HistoryStoragePeriod.FourteenDays
            ),
            SettingsChoice(
                id = Settings.HistoryStoragePeriod.ThirtyDays.name,
                title = LocalizedString.resource(R.string.settings_in_thirty_days),
                isChosen = historyStoragePeriod == Settings.HistoryStoragePeriod.ThirtyDays,
                output = Settings.HistoryStoragePeriod.ThirtyDays
            )
        ),
        keyboardLayoutTypes = listOf(
            SettingsChoice(
                id = Settings.KeyboardLayoutType.Classic.name,
                title = LocalizedString.resource(R.string.settings_phone_keyboard),
                isChosen = keyboardLayoutType == Settings.KeyboardLayoutType.Classic,
                output = Settings.KeyboardLayoutType.Classic
            ),
            SettingsChoice(
                id = Settings.KeyboardLayoutType.NumPad.name,
                title = LocalizedString.resource(R.string.settings_numpad_keyboard),
                isChosen = keyboardLayoutType == Settings.KeyboardLayoutType.NumPad,
                output = Settings.KeyboardLayoutType.NumPad
            )
        ),
        isKeepScreenOn = isKeepScreenOn,
        themeTypes = listOf(
            SettingsChoice(
                id = Settings.ThemeType.Light.name,
                title = LocalizedString.resource(R.string.settings_light_theme),
                isChosen = themeType == Settings.ThemeType.Light,
                output = Settings.ThemeType.Light
            ),
            SettingsChoice(
                id = Settings.ThemeType.Dark.name,
                title = LocalizedString.resource(R.string.settings_dark_theme),
                isChosen = themeType == Settings.ThemeType.Dark,
                output = Settings.ThemeType.Dark
            ),
            SettingsChoice(
                id = Settings.ThemeType.System.name,
                title = LocalizedString.resource(R.string.settings_system_theme),
                isChosen = themeType == Settings.ThemeType.System,
                output = Settings.ThemeType.System
            )
        ),
        versionDescription = LocalizedString.resource(
            R.string.settings_version,
            BuildConfig.VERSION_NAME
        )
    )
}

data class SettingsChoice<T>(
    val output: T,
    val title: LocalizedString,
    val isChosen: Boolean = false,
    val id: String
)

fun Banknote.toSettingsChoice(): SettingsChoice<Banknote> {
    return SettingsChoice(
        id = id.value,
        title = if (denomination < 1) {
            LocalizedString.resource(R.string.common_name_penny, name)
        } else {
            LocalizedString.resource(R.string.common_name_rub, name)
        },
        isChosen = isShow,
        output = this
    )
}