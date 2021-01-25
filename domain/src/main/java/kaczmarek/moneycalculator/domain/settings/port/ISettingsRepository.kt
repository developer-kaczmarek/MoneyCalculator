package kaczmarek.moneycalculator.domain.settings.port

interface ISettingsRepository {

    fun setHistoryStoragePeriod(period: Int)

    fun setKeyboardLayout(type: Int)

    fun setAlwaysBacklightOn(isOn: Boolean)

    fun getHistoryStoragePeriod(): Int

    fun getKeyboardLayout(): Int

    fun isAlwaysBacklightOn(): Boolean

    fun getCountMeetComponents(): Int

    fun updateCountMeetComponent(countMeetComponent: Int)
}