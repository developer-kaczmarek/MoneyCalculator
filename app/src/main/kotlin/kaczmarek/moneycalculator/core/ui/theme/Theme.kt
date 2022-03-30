package kaczmarek.moneycalculator.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@SuppressLint("UnrememberedMutableState")
@Composable
fun AppTheme(
    themeType: ThemeType = ThemeType.Default,
    content: @Composable () -> Unit
) {
    val materialColors = if (themeType == ThemeType.LightTheme) {
        getMaterialLightColors()
    } else {
        getMaterialDarkColors()
    }

    CompositionLocalProvider(
        LocalThemeType provides themeType
    ) {
        MaterialTheme(
            colors = materialColors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val LocalThemeType = staticCompositionLocalOf {
    ThemeType.Default
}

val MaterialTheme.themeType: ThemeType
    @Composable
    @ReadOnlyComposable
    get() = LocalThemeType.current