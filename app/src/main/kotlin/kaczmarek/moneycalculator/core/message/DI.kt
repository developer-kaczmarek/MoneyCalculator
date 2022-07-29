package kaczmarek.moneycalculator.core.message

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.message.ui.RealMessageComponent
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.message.ui.MessageComponent
import org.koin.core.component.get

fun ComponentFactory.createMessagesComponent(
    componentContext: ComponentContext
): MessageComponent {
    return RealMessageComponent(componentContext, get())
}