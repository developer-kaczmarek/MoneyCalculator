package kaczmarek.moneycalculator.core

import kaczmarek.moneycalculator.core.ui.ActivityProvider
import kaczmarek.moneycalculator.core.ui.message.MessageService
import kaczmarek.moneycalculator.core.ui.message.MessageServiceImpl
import org.koin.dsl.module

val coreModule = module {
    single { ActivityProvider() }
    single<MessageService> { MessageServiceImpl() }
}