package kaczmarek.moneycalculator.di.services

import android.content.SharedPreferences
import androidx.annotation.IntDef
import kaczmarek.moneycalculator.data.settings.port.ISettingsSharedPrefsService

/**
 * Created by Angelina Podbolotova on 20.10.2019.
 */

class SettingsSharedPrefsService(private val prefs: SharedPreferences) :
    ISettingsSharedPrefsService {

    override fun setHistoryStoragePeriod(@StoragePeriod period: Int) {
        prefs.edit().putInt(KEY_HISTORY_STORAGE_PERIOD, period).apply()
    }

    override fun setKeyboardLayout(@KeyboardLayout type: Int) {
        prefs.edit().putInt(KEY_KEYBOARD_LAYOUT, type).apply()
    }

    override fun setAlwaysBacklightOn(isOn: Boolean) {
        prefs.edit().putBoolean(KEY_ALWAYS_ON_DISPLAY, isOn).apply()
    }

    override fun getHistoryStoragePeriod(): Int =
        prefs.getInt(KEY_HISTORY_STORAGE_PERIOD, INDEFINITELY)

    override fun getKeyboardLayout(): Int = prefs.getInt(KEY_KEYBOARD_LAYOUT, CLASSIC)

    override fun isAlwaysBacklightOn(): Boolean = prefs.getBoolean(KEY_ALWAYS_ON_DISPLAY, false)

    override fun getCountMeetComponents(): Int = prefs.getInt(KEY_COUNT_MEET_COMPONENTS, 0)

    override fun updateCountMeetComponent(countMeetComponent: Int) {
        prefs.edit().putInt(KEY_COUNT_MEET_COMPONENTS, countMeetComponent).apply()
    }

    companion object {
        @IntDef(
            CLASSIC,
            NUMPAD
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class KeyboardLayout

        @IntDef(
            INDEFINITELY,
            FOURTEEN_DAYS,
            THIRTY_DAYS
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class StoragePeriod

        const val KEY_SETTINGS = "SETTINGS"
        const val KEY_KEYBOARD_LAYOUT = "KEY_KEYBOARD_LAYOUT"
        const val KEY_ALWAYS_ON_DISPLAY = "KEY_ALWAYS_ON_DISPLAY"
        const val KEY_HISTORY_STORAGE_PERIOD = "KEY_HISTORY_STORAGE_PERIOD"
        const val KEY_COUNT_MEET_COMPONENTS = "KEY_COUNT_MEET_COMPONENTS"

        const val CLASSIC = 0
        const val NUMPAD = 1

        const val INDEFINITELY = 0
        const val FOURTEEN_DAYS = 1
        const val THIRTY_DAYS = 2
    }
}