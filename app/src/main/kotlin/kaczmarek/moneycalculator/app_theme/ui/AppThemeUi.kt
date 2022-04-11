package kaczmarek.moneycalculator.app_theme.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.theme.ThemeType

@Composable
fun AppThemeUi(
    component: AppThemeComponent,
    content: @Composable () -> Unit
) {
    AppTheme(component.themeType) {
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppThemeUiPreview() {
    AppTheme {
       AppThemeUi(FakeAppThemeComponent()) {}
    }
}

class FakeAppThemeComponent : AppThemeComponent {

    override val themeType: ThemeType = ThemeType.Default

    override fun onThemeChange() = Unit
}