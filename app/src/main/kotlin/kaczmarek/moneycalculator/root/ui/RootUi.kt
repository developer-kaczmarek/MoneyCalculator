package kaczmarek.moneycalculator.root.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kaczmarek.moneycalculator.app_theme.ui.AppThemeUi
import kaczmarek.moneycalculator.app_theme.ui.FakeAppThemeComponent
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.theme.LocalThemeType
import kaczmarek.moneycalculator.core.theme.ThemeType
import kaczmarek.moneycalculator.core.utils.createFakeRouterState
import kaczmarek.moneycalculator.home.ui.FakeHomeComponent
import kaczmarek.moneycalculator.home.ui.HomeUi
import kaczmarek.moneycalculator.core.message.ui.FakeMessageComponent
import kaczmarek.moneycalculator.core.message.ui.MessageUi
import kaczmarek.moneycalculator.sessions.ui.details.SessionUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    AppThemeUi(component.appThemeComponent) {
        val systemUiController = rememberSystemUiController()
        val surfaceColor = MaterialTheme.colors.surface
        val statusBarDarkContentEnabled = LocalThemeType.current != ThemeType.DarkTheme
        LaunchedEffect(surfaceColor, statusBarDarkContentEnabled) {
            systemUiController.setStatusBarColor(surfaceColor)
            systemUiController.statusBarDarkContentEnabled = statusBarDarkContentEnabled
            systemUiController.setNavigationBarColor(surfaceColor)
        }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box {
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