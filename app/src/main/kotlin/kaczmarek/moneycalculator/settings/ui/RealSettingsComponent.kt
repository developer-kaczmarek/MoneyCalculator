package kaczmarek.moneycalculator.settings.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.domain.ChangeBanknoteVisibilityInteractor
import kaczmarek.moneycalculator.core.ui.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.ui.external_app_service.ExternalAppService
import kaczmarek.moneycalculator.core.ui.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.ui.utils.handleErrors
import kaczmarek.moneycalculator.core.ui.utils.toComposeState
import kaczmarek.moneycalculator.settings.domain.*
import kotlinx.coroutines.launch
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh

class RealSettingsComponent(
    componentContext: ComponentContext,
    private val onOutput: (SettingsComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    private val externalAppService: ExternalAppService,
    getSettingsInteractor: GetSettingsInteractor,
    private val changeKeyboardLayoutTypeInteractor: ChangeKeyboardLayoutTypeInteractor,
    private val changeHistoryStoragePeriodInteractor: ChangeHistoryStoragePeriodInteractor,
    private val changeBanknoteVisibilityInteractor: ChangeBanknoteVisibilityInteractor,
    private val changeDisplayAlwaysOnInteractor: ChangeDisplayAlwaysOnInteractor,
    private val changeThemeTypeInteractor: ChangeThemeTypeInteractor,
) : ComponentContext by componentContext, SettingsComponent {

    companion object {
        private const val SUPPORT_EMAIL = "developer.kaczmarek@yandex.ru"
        private const val GITHUB_PAGE_URL = "https://github.com/developer-kaczmarek/MoneyCalculator"
        private const val PRIVACY_POLICY_PAGE_URL = "https://github.com/developer-kaczmarek/MoneyCalculator/blob/main/privacy_policy.pdf"
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
        coroutineScope.launch {
            changeBanknoteVisibilityInteractor.execute(banknote.copy(isShow = !banknote.isShow))
            settingsLoading.refresh()
        }
    }

    override fun onKeyboardLayoutTypeClick(keyboardLayoutType: Settings.KeyboardLayoutType) {
        coroutineScope.launch {
            changeKeyboardLayoutTypeInteractor.execute(keyboardLayoutType)
            settingsLoading.refresh()
        }
    }

    override fun onHistoryStoragePeriodClick(historyStoragePeriod: Settings.HistoryStoragePeriod) {
        coroutineScope.launch {
            changeHistoryStoragePeriodInteractor.execute(historyStoragePeriod)
            settingsLoading.refresh()
        }
    }

    override fun onContactDeveloperClick() {
        coroutineScope.launch {
            externalAppService.sendEmail(SUPPORT_EMAIL)
        }
    }

    override fun onPrivacyPolicyClick() {
        coroutineScope.launch {
            externalAppService.openBrowser(PRIVACY_POLICY_PAGE_URL)
        }
    }

    override fun onGithubClick() {
        coroutineScope.launch {
            externalAppService.openBrowser(GITHUB_PAGE_URL)
        }
    }

    override fun onGooglePlayClick() {
        coroutineScope.launch {
            externalAppService.openPageOnGooglePlay()
        }
    }

    override fun onDisplayAlwaysOnClick(oldCheckedValue: Boolean) {
        coroutineScope.launch {
            changeDisplayAlwaysOnInteractor.execute(!oldCheckedValue)
            settingsLoading.refresh()
        }
    }

    override fun onThemeTypeClick(themeType: Settings.ThemeType) {
        coroutineScope.launch {
            changeThemeTypeInteractor.execute(themeType)
            settingsLoading.refresh()
            onOutput(SettingsComponent.Output.ThemeChanged)
        }
    }
}