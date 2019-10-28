package com.kaczmarek.moneycalculator.di

import android.content.SharedPreferences
import androidx.annotation.IntDef

/**
 * Created by Angelina Podbolotova on 20.10.2019.
 */

class SettingsService(private val prefs: SharedPreferences) {

    var isAlwaysOnDisplay: Boolean
        get() = prefs.getBoolean(KEY_ALWAYS_ON_DISPLAY, false)
        set(isAlwaysOnDisplay) {
            prefs.edit().putBoolean(KEY_ALWAYS_ON_DISPLAY, isAlwaysOnDisplay).apply()
        }

    var keyboardLayout: Int
        get() = prefs.getInt(KEY_KEYBOARD_LAYOUT, CLASSIC)
        set(@KeyboardLayout keyboardLayout) {
            prefs.edit().putInt(KEY_KEYBOARD_LAYOUT, keyboardLayout).apply()
        }

    var historyStoragePeriod: Int
        get() = prefs.getInt(KEY_HISTORY_STORAGE_PERIOD, INDEFINITELY)
        set(@StoragePeriod historyStoragePeriod) {
            prefs.edit().putInt(KEY_HISTORY_STORAGE_PERIOD, historyStoragePeriod).apply()
        }

    companion object {
        @IntDef(CLASSIC, NUMPAD)
        @Retention(AnnotationRetention.SOURCE)
        annotation class KeyboardLayout

        @IntDef(INDEFINITELY, FOURTEEN_DAYS, THIRTY_DAYS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class StoragePeriod

        const val KEY_SETTINGS = "SETTINGS"
        const val KEY_KEYBOARD_LAYOUT = "KEY_KEYBOARD_LAYOUT"
        const val KEY_ALWAYS_ON_DISPLAY = "KEY_ALWAYS_ON_DISPLAY"
        const val KEY_HISTORY_STORAGE_PERIOD = "KEY_HISTORY_STORAGE_PERIOD"

        const val CLASSIC = 0
        const val NUMPAD = 1

        const val INDEFINITELY = 0
        const val FOURTEEN_DAYS = 1
        const val THIRTY_DAYS = 2

    }
}