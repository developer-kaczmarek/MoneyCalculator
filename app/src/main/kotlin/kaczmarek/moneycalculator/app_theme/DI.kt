package kaczmarek.moneycalculator.app_theme

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.app_theme.ui.AppThemeComponent
import kaczmarek.moneycalculator.app_theme.ui.RealAppThemeComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import org.koin.dsl.module

val appThemeModule = module {

}

fun ComponentFactory.createAppThemeComponent(
    componentContext: ComponentContext,
): AppThemeComponent {
    return RealAppThemeComponent(componentContext)
}