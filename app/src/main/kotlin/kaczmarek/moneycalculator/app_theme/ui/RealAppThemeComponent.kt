package kaczmarek.moneycalculator.app_theme.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ui.theme.ThemeType

class RealAppThemeComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, AppThemeComponent {

    override val themeType by derivedStateOf {
        ThemeType.Default
    }

    override fun onThemeChange() {
        // TODO: Change theme
    }
}