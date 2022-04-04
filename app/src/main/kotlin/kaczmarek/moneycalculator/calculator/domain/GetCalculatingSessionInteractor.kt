package kaczmarek.moneycalculator.calculator.domain

import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesStorage
import kaczmarek.moneycalculator.settings.data.SettingsStorage

class GetCalculatingSessionInteractor(
    private val banknotesStorage: BanknotesStorage,
    private val settingsStorage: SettingsStorage
) {

    suspend fun execute(): CalculatingSession {
        return CalculatingSession(
            banknotes = banknotesStorage.getVisibleBanknotes(),
            keyboardLayoutType = settingsStorage.getKeyboardLayoutType(),
            isKeepScreenOn = settingsStorage.isKeepScreenOn()
        )
    }
}