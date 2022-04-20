package kaczmarek.moneycalculator.root.ui

import com.arkivanov.decompose.router.RouterState
import kaczmarek.moneycalculator.app_theme.ui.AppThemeComponent
import kaczmarek.moneycalculator.home.ui.HomeComponent
import kaczmarek.moneycalculator.core.message.ui.MessageComponent
import kaczmarek.moneycalculator.sessions.ui.details.SessionComponent

interface RootComponent {

    val appThemeComponent: AppThemeComponent
    val messageComponent: MessageComponent
    val routerState: RouterState<*, Child>

    sealed interface Child {

        class Home(val component: HomeComponent) : Child

        class DetailedSession(val component: SessionComponent) : Child
    }
}