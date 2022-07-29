package kaczmarek.moneycalculator.core.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import kaczmarek.moneycalculator.core.utils.LocalMessageOffsets

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

    val messageOffset = remember { mutableStateMapOf<Int, Int>() }

    CompositionLocalProvider(
        LocalThemeType provides themeType,
        LocalMessageOffsets provides messageOffset,
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

val themeType: ThemeType
    @Composable
    @ReadOnlyComposable
    get() = LocalThemeType.current