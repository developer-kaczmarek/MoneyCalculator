package kaczmarek.moneycalculator.settings.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.banknote.domain.Banknote
import kaczmarek.moneycalculator.core.banknote.domain.ChangeBanknoteVisibilityInteractor
import kaczmarek.moneycalculator.core.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.error_handling.safeLaunch
import kaczmarek.moneycalculator.core.error_handling.safeRun
import kaczmarek.moneycalculator.core.external_app_service.ExternalAppService
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.utils.handleErrors
import kaczmarek.moneycalculator.core.utils.toComposeState
import kaczmarek.moneycalculator.settings.domain.*
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.dataOrNull
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh
import me.aartikov.sesame.localizedstring.LocalizedString

class RealSettingsComponent(
    componentContext: ComponentContext,
    private val onOutput: (SettingsComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    private val messageService: MessageService,
    private val externalAppService: ExternalAppService,
    getSettingsInteractor: GetSettingsInteractor,
    private val changeKeyboardLayoutTypeInteractor: ChangeKeyboardLayoutTypeInteractor,
    private val changeHistoryStoragePeriodInteractor: ChangeHistoryStoragePeriodInteractor,
    private val changeBanknoteVisibilityInteractor: ChangeBanknoteVisibilityInteractor,
    private val changeKeepScreenOnInteractor: ChangeKeepScreenOnInteractor,
    private val changeThemeTypeInteractor: ChangeThemeTypeInteractor,
) : ComponentContext by componentContext, SettingsComponent {

    companion object {
        private const val SUPPORT_EMAIL = "developer.kaczmarek@yandex.ru"
        private const val GITHUB_PAGE_URL = "https://github.com/developer-kaczmarek/MoneyCalculator"
        private const val PRIVACY_POLICY_PAGE_URL =
            "https://github.com/developer-kaczmarek/MoneyCalculator/blob/master/privacy_policy.pdf"
    }

    private val coroutineScope = componentCoroutineScope()

    private val settingsLoading = OrdinaryLoading(
        coroutineScope,
        load = { getSettingsInteractor.execute() }
    )

    private val settingsState by settingsLoading.stateFlow.toComposeState(coroutineScope)

    override val settingsViewState by derivedStateOf {
        settingsState.mapData { it.toViewData() }
    }

    init {
        lifecycle.doOnCreate {
            settingsLoading.handleErrors(coroutineScope, errorHandler)
            settingsLoading.refresh()
        }
    }

    override fun onBanknoteClick(banknote: Banknote) {
        coroutineScope.safeLaunch(errorHandler) {
            val visibilityBanknotes = settingsState.dataOrNull?.banknotes?.filter { it.isShow }
            if (visibilityBanknotes?.count() == 1 && visibilityBanknotes.first().id == banknote.id) {
                messageService.showMessage(MessageData(LocalizedString.resource(R.string.settings_all_banknotes_invisible)))
            } else {
                changeBanknoteVisibilityInteractor.execute(banknote.copy(isShow = !banknote.isShow))
                settingsLoading.refresh()
            }
        }
    }

    override fun onKeyboardLayoutTypeClick(keyboardLayoutType: Settings.KeyboardLayoutType) {
        safeRun(errorHandler) {
            changeKeyboardLayoutTypeInteractor.execute(keyboardLayoutType)
            settingsLoading.refresh()
        }
    }

    override fun onHistoryStoragePeriodClick(historyStoragePeriod: Settings.HistoryStoragePeriod) {
        safeRun(errorHandler) {
            changeHistoryStoragePeriodInteractor.execute(historyStoragePeriod)
            settingsLoading.refresh()
        }
    }

    override fun onContactDeveloperClick() {
        safeRun(errorHandler) {
            externalAppService.sendEmail(SUPPORT_EMAIL)
        }
    }

    override fun onPrivacyPolicyClick() {
        safeRun(errorHandler) {
            externalAppService.openBrowser(PRIVACY_POLICY_PAGE_URL)
        }
    }

    override fun onGithubClick() {
        safeRun(errorHandler) {
            externalAppService.openBrowser(GITHUB_PAGE_URL)
        }
    }

    override fun onGooglePlayClick() {
        safeRun(errorHandler) {
            externalAppService.openPageOnGooglePlay()
        }
    }

    override fun onKeepScreenOnClick(oldCheckedValue: Boolean) {
        safeRun(errorHandler) {
            changeKeepScreenOnInteractor.execute(!oldCheckedValue)
            settingsLoading.refresh()
        }
    }

    override fun onThemeTypeClick(themeType: Settings.ThemeType) {
        safeRun(errorHandler) {
            changeThemeTypeInteractor.execute(themeType)
            settingsLoading.refresh()
            onOutput(SettingsComponent.Output.ThemeChanged)
        }
    }
}