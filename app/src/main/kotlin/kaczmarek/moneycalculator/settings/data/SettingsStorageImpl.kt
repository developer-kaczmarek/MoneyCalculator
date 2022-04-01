package kaczmarek.moneycalculator.settings.data

import android.content.SharedPreferences
import androidx.core.content.edit
import kaczmarek.moneycalculator.settings.domain.Settings

class SettingsStorageImpl(private val prefs: SharedPreferences) : SettingsStorage {

    companion object {
        private const val KEYBOARD_LAYOUT_KEY = "KEY_KEYBOARD_LAYOUT"
        private const val ALWAYS_ON_DISPLAY_KEY = "KEY_ALWAYS_ON_DISPLAY"
        private const val HISTORY_STORAGE_PERIOD_KEY = "KEY_HISTORY_STORAGE_PERIOD"
        private const val THEME_TYPE_KEY = "THEME_TYPE_KEY"
    }

    override fun getKeyboardLayoutType(): Settings.KeyboardLayoutType {
        return when (prefs.getInt(KEYBOARD_LAYOUT_KEY, 0)) {
            Settings.KeyboardLayoutType.NumPad.id -> Settings.KeyboardLayoutType.NumPad
            else -> Settings.KeyboardLayoutType.Classic
        }
    }

    override fun updateKeyboardLayoutType(keyboardLayoutType: Settings.KeyboardLayoutType) {
        prefs.edit { putInt(KEYBOARD_LAYOUT_KEY, keyboardLayoutType.id) }
    }

    override fun getHistoryStoragePeriod(): Settings.HistoryStoragePeriod {
        return when (prefs.getInt(HISTORY_STORAGE_PERIOD_KEY, 0)) {
            Settings.HistoryStoragePeriod.FourteenDays.id -> Settings.HistoryStoragePeriod.FourteenDays
            Settings.HistoryStoragePeriod.ThirtyDays.id -> Settings.HistoryStoragePeriod.ThirtyDays
            else -> Settings.HistoryStoragePeriod.Indefinitely
        }
    }

    override fun updateHistoryStoragePeriod(historyStoragePeriod: Settings.HistoryStoragePeriod) {
        prefs.edit { putInt(HISTORY_STORAGE_PERIOD_KEY, historyStoragePeriod.id) }
    }

    override fun isDisplayAlwaysOn(): Boolean {
        return prefs.getBoolean(ALWAYS_ON_DISPLAY_KEY, false)
    }

    override fun updateDisplayAlwaysOn(checked: Boolean) {
        prefs.edit { putBoolean(ALWAYS_ON_DISPLAY_KEY, checked) }
    }

    override fun getThemeType(): Settings.ThemeType {
        val name = prefs.getString(THEME_TYPE_KEY, null)
        return name?.let { Settings.ThemeType.valueOf(it) } ?: Settings.ThemeType.Light
    }

    override fun updateThemeType(themeType: Settings.ThemeType) {
        prefs.edit { putString(THEME_TYPE_KEY, themeType.name) }
    }
}