package kaczmarek.moneycalculator.calculator

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.calculator.domain.GetCalculatingSessionInteractor
import kaczmarek.moneycalculator.calculator.ui.CalculatorComponent
import kaczmarek.moneycalculator.calculator.ui.RealCalculatorComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import org.koin.core.component.get
import org.koin.dsl.module

val calculatorModule = module {
    factory { GetCalculatingSessionInteractor(get(), get()) }
}

fun ComponentFactory.createCalculatorComponent(
    componentContext: ComponentContext
): CalculatorComponent {
    return RealCalculatorComponent(componentContext, get(), get(), get(), get())
}