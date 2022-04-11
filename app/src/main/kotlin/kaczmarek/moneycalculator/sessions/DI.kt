package kaczmarek.moneycalculator.sessions

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.storage.MoneyCalculatorDatabase
import kaczmarek.moneycalculator.sessions.data.SessionsStorage
import kaczmarek.moneycalculator.sessions.data.SessionsStorageImpl
import kaczmarek.moneycalculator.sessions.domain.DeleteOldSessionsIfNeedInteractor
import kaczmarek.moneycalculator.sessions.domain.GetSessionsInteractor
import kaczmarek.moneycalculator.sessions.domain.SaveSessionInteractor
import kaczmarek.moneycalculator.sessions.ui.SessionsComponent
import kaczmarek.moneycalculator.sessions.ui.RealSessionsComponent
import org.koin.core.component.get
import org.koin.dsl.module

val sessionsModule = module {
    single { get<MoneyCalculatorDatabase>().getSessionsDao() }
    single<SessionsStorage> { SessionsStorageImpl(get()) }
    factory { GetSessionsInteractor(get()) }
    factory { SaveSessionInteractor(get()) }
    factory { DeleteOldSessionsIfNeedInteractor(get(), get()) }
}

fun ComponentFactory.createSessionComponent(
    componentContext: ComponentContext
): SessionsComponent {
    return RealSessionsComponent(componentContext, get(), get(), get())
}