package kaczmarek.moneycalculator.calculator

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.calculator.ui.CalculatorComponent
import kaczmarek.moneycalculator.calculator.ui.RealCalculatorComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import org.koin.dsl.module

val calculatorModule = module {

}

fun ComponentFactory.createCalculatorComponent(
    componentContext: ComponentContext
): CalculatorComponent {
    return RealCalculatorComponent(componentContext)
}