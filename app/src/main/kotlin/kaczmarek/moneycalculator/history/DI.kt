package kaczmarek.moneycalculator.history

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.history.ui.HistoryComponent
import kaczmarek.moneycalculator.history.ui.RealHistoryComponent
import org.koin.dsl.module

val historyModule = module {}

fun ComponentFactory.createHistoryComponent(
    componentContext: ComponentContext
): HistoryComponent {
    return RealHistoryComponent(componentContext)
}