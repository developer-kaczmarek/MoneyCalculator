package kaczmarek.moneycalculator.app_theme.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kaczmarek.moneycalculator.app_theme.domain.GetThemeTypeInteractor
import kaczmarek.moneycalculator.core.ui.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.ui.theme.ThemeType
import kaczmarek.moneycalculator.core.ui.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.ui.utils.handleErrors
import kaczmarek.moneycalculator.core.ui.utils.toComposeState
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.dataOrNull
import me.aartikov.sesame.loading.simple.refresh

class RealAppThemeComponent(
    componentContext: ComponentContext,
    private val getThemeTypeInteractor: GetThemeTypeInteractor,
    private val errorHandler: ErrorHandler
) : ComponentContext by componentContext, AppThemeComponent {

    private val coroutineScope = componentCoroutineScope()

    private val themeTypeLoading = OrdinaryLoading(
        scope = coroutineScope,
        load = { getThemeTypeInteractor.execute() }
    )
    private val themeTypeState by themeTypeLoading.stateFlow.toComposeState(
        coroutineScope
    )

    override val themeType by derivedStateOf {
        when (themeTypeState.dataOrNull) {
            Settings.ThemeType.Dark -> ThemeType.DarkTheme
            Settings.ThemeType.System -> ThemeType.SystemTheme
            Settings.ThemeType.Light -> ThemeType.LightTheme
            else -> ThemeType.Default
        }
    }

    init {
        lifecycle.doOnCreate {
            themeTypeLoading.handleErrors(coroutineScope, errorHandler)
            themeTypeLoading.refresh()
        }
    }

    override fun onThemeChange() {
        themeTypeLoading.refresh()
    }
}