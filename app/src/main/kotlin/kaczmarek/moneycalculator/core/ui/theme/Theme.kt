package kaczmarek.moneycalculator.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
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
    val materialColors = when (themeType) {
        ThemeType.DarkTheme -> getMaterialDarkColors()
        ThemeType.LightTheme -> getMaterialLightColors()
        ThemeType.SystemTheme -> {
            if (isSystemInDarkTheme()) {
                getMaterialDarkColors()
            } else {
                getMaterialLightColors()
            }
        }
    }

    if (themeType == ThemeType.SystemTheme) {
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