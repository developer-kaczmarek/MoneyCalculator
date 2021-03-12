package kaczmarek.moneycalculator.data.settings

import kaczmarek.moneycalculator.domain.settings.port.ISettingsRepository

class SettingsRepository(
    private val settingsService: ISettingsSharedPrefsService
) : ISettingsRepository {
    override fun setHistoryStoragePeriod(period: Int) {
        settingsService.setHistoryStoragePeriod(period)
    }

    override fun setKeyboardLayout(type: Int) {
        settingsService.setKeyboardLayout(type)
    }

    override fun setAlwaysBacklightOn(isOn: Boolean) {
        settingsService.setAlwaysBacklightOn(isOn)
    }

    override fun updateCountMeetComponent(countMeetComponent: Int) {
        settingsService.updateCountMeetComponent(countMeetComponent)
    }


    override fun getHistoryStoragePeriod(): Int = settingsService.getHistoryStoragePeriod()

    override fun getKeyboardLayout(): Int = settingsService.getKeyboardLayout()

    override fun isAlwaysBacklightOn(): Boolean = settingsService.isAlwaysBacklightOn()

    override fun getCountMeetComponents(): Int = settingsService.getCountMeetComponents()

}