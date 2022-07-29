package kaczmarek.moneycalculator.root.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kaczmarek.moneycalculator.app_theme.ui.AppThemeUi
import kaczmarek.moneycalculator.app_theme.ui.FakeAppThemeComponent
import kaczmarek.moneycalculator.core.message.ui.FakeMessageComponent
import kaczmarek.moneycalculator.core.message.ui.MessageUi
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.utils.createFakeRouterState
import kaczmarek.moneycalculator.home.ui.FakeHomeComponent
import kaczmarek.moneycalculator.home.ui.HomeUi
import kaczmarek.moneycalculator.sessions.ui.details.SessionUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    AppThemeUi(component.appThemeComponent) {

        SystemBarColors()

        Surface(modifier = modifier.fillMaxSize()) {
            Box(modifier = Modifier.systemBarsPadding()) {
                Children(component.routerState) { child ->
                    when (val instance = child.instance) {
                        is RootComponent.Child.Home -> HomeUi(instance.component)
                        is RootComponent.Child.DetailedSession -> SessionUi(instance.component)
                    }
                }

                MessageUi(component.messageComponent)
            }
        }
    }
}

@Composable
private fun SystemBarColors() {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colors.surface
    LaunchedEffect(statusBarColor) {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    val navigationBarColor = MaterialTheme.colors.surface
    LaunchedEffect(navigationBarColor) {
        systemUiController.setNavigationBarColor(navigationBarColor)
    }

    val statusBarDarkContentEnabled = statusBarColor.luminance() > 0.5f
    LaunchedEffect(statusBarDarkContentEnabled) {
        systemUiController.statusBarDarkContentEnabled = statusBarDarkContentEnabled
        systemUiController.navigationBarDarkContentEnabled = statusBarDarkContentEnabled
    }
}

@Preview(showSystemUi = true)
@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}

class FakeRootComponent : RootComponent {
    override val appThemeComponent = FakeAppThemeComponent()
    override val messageComponent = FakeMessageComponent()
    override val routerState = createFakeRouterState(
        RootComponent.Child.Home(FakeHomeComponent())
    )
}