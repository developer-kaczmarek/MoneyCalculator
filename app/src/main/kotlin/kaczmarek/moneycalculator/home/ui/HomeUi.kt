package kaczmarek.moneycalculator.home.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import kaczmarek.moneycalculator.calculator.ui.CalculatorUi
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.utils.createFakeRouterState
import kaczmarek.moneycalculator.core.utils.noOverlapByMessage
import kaczmarek.moneycalculator.settings.ui.SettingsUi
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.utils.currentInstance
import kaczmarek.moneycalculator.sessions.ui.SessionsUi
import kaczmarek.moneycalculator.settings.ui.FakeSettingsUiComponent

@Composable
fun HomeUi(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val currentChild: HomeComponent.Child = component.routerState.currentInstance
    Scaffold(
        modifier = modifier,
        bottomBar = { BottomBar(currentChild, component::onPageSelected) },
        content = { paddingValues ->
            Children(component.routerState, Modifier.padding(paddingValues)) { child ->
                when (val instance = child.instance) {
                    is HomeComponent.Child.Calculator -> CalculatorUi(instance.component)
                    is HomeComponent.Child.History -> SessionsUi(instance.component)
                    is HomeComponent.Child.Settings -> SettingsUi(instance.component)
                }
            }
        }
    )
}

@Composable
fun BottomBar(currentChild: HomeComponent.Child, onPageSelected: (HomeComponent.Page) -> Unit) {
    BottomNavigation(
        modifier = Modifier.noOverlapByMessage(),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        NavigationItem(
            iconRes = R.drawable.ic_24_home,
            labelRes = R.string.home_calculator_name,
            isSelected = currentChild is HomeComponent.Child.Calculator,
            onClick = { onPageSelected(HomeComponent.Page.Calculator) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_history,
            labelRes = R.string.home_history_name,
            isSelected = currentChild is HomeComponent.Child.History,
            onClick = { onPageSelected(HomeComponent.Page.History) }
        )

        NavigationItem(
            iconRes = R.drawable.ic_24_settings,
            labelRes = R.string.home_settings_name,
            isSelected = currentChild is HomeComponent.Child.Settings,
            onClick = { onPageSelected(HomeComponent.Page.Settings) }
        )
    }
}

@Composable
fun RowScope.NavigationItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(iconRes), contentDescription = null) },
        label = { Text(stringResource(labelRes), maxLines = 1, overflow = TextOverflow.Ellipsis) },
        selected = isSelected,
        onClick = onClick,
        selectedContentColor = MaterialTheme.colors.onBackground,
        unselectedContentColor = MaterialTheme.colors.onSurface
    )
}

@Preview(showSystemUi = true)
@Composable
fun HomeUiPreview() {
    AppTheme {
        HomeUi(FakeHomeComponent())
    }
}

class FakeHomeComponent : HomeComponent {
    override val routerState = createFakeRouterState(
        HomeComponent.Child.Settings(FakeSettingsUiComponent())
    )

    override fun onPageSelected(page: HomeComponent.Page) = Unit
}