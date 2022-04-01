package kaczmarek.moneycalculator.settings.domain

import kaczmarek.moneycalculator.core.domain.GetBanknotesInteractor
import kaczmarek.moneycalculator.settings.data.SettingsStorage

class GetSettingsInteractor(
    private val settingsStorage: SettingsStorage,
    private val getBanknotesInteractor: GetBanknotesInteractor
) {

    suspend fun execute(): Settings {
        return Settings(
            banknotes = getBanknotesInteractor.execute(),
            historyStoragePeriod = settingsStorage.getHistoryStoragePeriod(),
            keyboardLayoutType = settingsStorage.getKeyboardLayoutType(),
            isDisplayAlwaysOn = settingsStorage.isDisplayAlwaysOn(),
            themeType = settingsStorage.getThemeType()
        )
    }
}