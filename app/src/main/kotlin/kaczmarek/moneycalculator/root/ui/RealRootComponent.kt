package kaczmarek.moneycalculator.root.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import kaczmarek.moneycalculator.app_theme.createAppThemeComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.utils.toComposeState
import kaczmarek.moneycalculator.home.createHomeComponent
import kaczmarek.moneycalculator.home.ui.HomeComponent
import kaczmarek.moneycalculator.core.message.createMessagesComponent
import kotlinx.parcelize.Parcelize

class RealRootComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, RootComponent {

    private val router = router<ChildConfig, RootComponent.Child>(
        initialConfiguration = ChildConfig.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, RootComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    override val appThemeComponent =
        componentFactory.createAppThemeComponent(childContext(key = "app_theme"))

    override val messageComponent =
        componentFactory.createMessagesComponent(childContext(key = "message"))

    private fun createChild(config: ChildConfig, componentContext: ComponentContext) =
        when (config) {
            is ChildConfig.Home -> RootComponent.Child.Home(
                componentFactory.createHomeComponent(
                    componentContext,
                    ::onHomeOutput,
                )
            )
        }

    private fun onHomeOutput(output: HomeComponent.Output) {
        when (output) {
            is HomeComponent.Output.ThemeChanged -> appThemeComponent.onThemeChange()
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object Home : ChildConfig
    }
}