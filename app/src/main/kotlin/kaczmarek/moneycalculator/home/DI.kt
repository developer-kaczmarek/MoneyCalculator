package kaczmarek.moneycalculator.home

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.home.ui.HomeComponent
import kaczmarek.moneycalculator.home.ui.RealHomeComponent
import org.koin.core.component.get

fun ComponentFactory.createHomeComponent(
    componentContext: ComponentContext,
    onOutput: (HomeComponent.Output) -> Unit
): HomeComponent {
    return RealHomeComponent(componentContext, onOutput, get())
}