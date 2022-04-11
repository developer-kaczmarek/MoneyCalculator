package kaczmarek.moneycalculator.core.theme

enum class ThemeType {
    DarkTheme,
    SystemTheme,
    LightTheme;

    companion object {
        val Default = LightTheme
    }
}