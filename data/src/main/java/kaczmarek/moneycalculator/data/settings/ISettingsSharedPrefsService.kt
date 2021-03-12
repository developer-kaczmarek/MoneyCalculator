package kaczmarek.moneycalculator.data.settings

interface ISettingsSharedPrefsService {

    fun setHistoryStoragePeriod(period: Int)

    fun setKeyboardLayout(type: Int)

    fun setAlwaysBacklightOn(isOn: Boolean)

    fun getHistoryStoragePeriod(): Int

    fun getKeyboardLayout(): Int

    fun isAlwaysBacklightOn(): Boolean

    fun getCountMeetComponents(): Int

    fun updateCountMeetComponent(countMeetComponent: Int)

}