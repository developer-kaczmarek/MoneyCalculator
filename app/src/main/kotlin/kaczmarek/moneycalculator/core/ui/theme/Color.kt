package kaczmarek.moneycalculator.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object RawColors {
    val black: Color = Color(0xFF000000)
    val darkGray: Color = Color(0xFF282828)
    val gray: Color = Color(0xFFA1A1A1)
    val lightGray: Color = Color(0xFFF2F2F2)
    val white: Color = Color(0xFFFFFFFF)
}

fun getMaterialLightColors(): Colors {
    return lightColors(
        primary = RawColors.black,
        primaryVariant = RawColors.darkGray,
        secondary = RawColors.black,
        secondaryVariant = RawColors.darkGray,
        background = RawColors.white,
        surface = RawColors.lightGray,
        onPrimary = RawColors.white,
        onSecondary = RawColors.white,
        onBackground = RawColors.black,
        onSurface = RawColors.gray
    )
}

fun getMaterialDarkColors(): Colors {
    return darkColors(
        primary = RawColors.white,
        primaryVariant = RawColors.lightGray,
        secondary = RawColors.white,
        secondaryVariant = RawColors.lightGray,
        background = RawColors.black,
        surface = RawColors.darkGray,
        onPrimary = RawColors.black,
        onSecondary = RawColors.black,
        onBackground = RawColors.white,
        onSurface = RawColors.gray
    )
}