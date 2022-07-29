package kaczmarek.moneycalculator.app_theme.ui

import kaczmarek.moneycalculator.core.theme.ThemeType

interface AppThemeComponent {

    val themeType: ThemeType

    fun onThemeChange()
}