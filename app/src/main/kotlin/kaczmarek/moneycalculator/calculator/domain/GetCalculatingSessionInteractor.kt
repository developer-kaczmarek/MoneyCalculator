package kaczmarek.moneycalculator.calculator.domain

import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorage
import kaczmarek.moneycalculator.settings.data.SettingsStorage

class GetCalculatingSessionInteractor(
    private val banknotesStorage: BanknotesStorage,
    private val settingsStorage: SettingsStorage
) {

    suspend fun execute(): CalculatingSession {
        val session = CalculatingSession(
            banknotes = banknotesStorage.getVisibleBanknotes(),
            keyboardLayoutType = settingsStorage.getKeyboardLayoutType(),
            isKeepScreenOn = settingsStorage.isKeepScreenOn()
        )
        banknotesStorage.resetBanknotesVisibilityChangedSettings()
        settingsStorage.resetSettingsChangedState()
        return session
    }
}