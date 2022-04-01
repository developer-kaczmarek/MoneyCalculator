package kaczmarek.moneycalculator.app_theme

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.app_theme.domain.GetThemeTypeInteractor
import kaczmarek.moneycalculator.app_theme.ui.AppThemeComponent
import kaczmarek.moneycalculator.app_theme.ui.RealAppThemeComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import org.koin.core.component.get
import org.koin.dsl.module

val appThemeModule = module {
    factory { GetThemeTypeInteractor(get()) }
}

fun ComponentFactory.createAppThemeComponent(
    componentContext: ComponentContext,
): AppThemeComponent {
    return RealAppThemeComponent(componentContext, get(), get())
}