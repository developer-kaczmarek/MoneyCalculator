package kaczmarek.moneycalculator.root

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.root.ui.RealRootComponent
import kaczmarek.moneycalculator.root.ui.RootComponent
import org.koin.core.component.get

fun ComponentFactory.createRootComponent(componentContext: ComponentContext): RootComponent {
    return RealRootComponent(componentContext, get())
}