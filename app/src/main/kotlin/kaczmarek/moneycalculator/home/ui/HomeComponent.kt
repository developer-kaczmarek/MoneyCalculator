package kaczmarek.moneycalculator.home.ui

import com.arkivanov.decompose.router.RouterState
import kaczmarek.moneycalculator.calculator.ui.CalculatorComponent
import kaczmarek.moneycalculator.sessions.ui.SessionsComponent
import kaczmarek.moneycalculator.settings.ui.SettingsComponent

interface HomeComponent {

    val routerState: RouterState<*, Child>

    enum class Page {
        Calculator, History, Settings
    }

    sealed interface Output {
        object ThemeChanged : Output
    }

    sealed interface Child {
        class Calculator(val component: CalculatorComponent) : Child
        class History(val component: SessionsComponent) : Child
        class Settings(val component: SettingsComponent) : Child
    }

    fun onPageSelected(page: Page)
}