package kaczmarek.moneycalculator.home.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import kaczmarek.moneycalculator.calculator.createCalculatorComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.ui.utils.toComposeState
import kaczmarek.moneycalculator.sessions.createSessionComponent
import kaczmarek.moneycalculator.settings.createSettingsComponent
import kaczmarek.moneycalculator.settings.ui.SettingsComponent
import kotlinx.parcelize.Parcelize

class RealHomeComponent(
    componentContext: ComponentContext,
    private val onOutput: (HomeComponent.Output) -> Unit,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, HomeComponent {

    private val router = router<ChildConfig, HomeComponent.Child>(
        initialConfiguration = ChildConfig.Calculator,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, HomeComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    override fun onPageSelected(page: HomeComponent.Page) {
        val configuration = when (page) {
            HomeComponent.Page.Calculator -> ChildConfig.Calculator
            HomeComponent.Page.History -> ChildConfig.History
            HomeComponent.Page.Settings -> ChildConfig.Settings
        }
        router.bringToFront(configuration)
    }

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): HomeComponent.Child =
        when (config) {
            is ChildConfig.Calculator -> HomeComponent.Child.Calculator(
                componentFactory.createCalculatorComponent(componentContext)
            )

            is ChildConfig.History -> HomeComponent.Child.History(
                componentFactory.createSessionComponent(componentContext)
            )

            is ChildConfig.Settings -> HomeComponent.Child.Settings(
                componentFactory.createSettingsComponent(componentContext, ::onSettingsOutput)
            )
        }

    private fun onSettingsOutput(output: SettingsComponent.Output) {
        when (output) {
            is SettingsComponent.Output.ThemeChanged -> {
                onOutput(HomeComponent.Output.ThemeChanged)
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object Calculator : ChildConfig

        @Parcelize
        object History : ChildConfig

        @Parcelize
        object Settings : ChildConfig
    }
}