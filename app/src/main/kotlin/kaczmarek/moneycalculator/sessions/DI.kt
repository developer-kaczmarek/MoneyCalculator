package kaczmarek.moneycalculator.sessions

import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.storage.MoneyCalculatorDatabase
import kaczmarek.moneycalculator.sessions.data.SessionsStorage
import kaczmarek.moneycalculator.sessions.data.SessionsStorageImpl
import kaczmarek.moneycalculator.sessions.domain.*
import kaczmarek.moneycalculator.sessions.ui.details.RealSessionComponent
import kaczmarek.moneycalculator.sessions.ui.details.SessionComponent
import kaczmarek.moneycalculator.sessions.ui.list.SessionsComponent
import kaczmarek.moneycalculator.sessions.ui.list.RealSessionsComponent
import org.koin.core.component.get
import org.koin.dsl.module

val sessionsModule = module {
    single { get<MoneyCalculatorDatabase>().getSessionsDao() }
    single<SessionsStorage> { SessionsStorageImpl(get()) }
    factory { GetSessionsInteractor(get()) }
    factory { SaveSessionInteractor(get()) }
    factory { DeleteOldSessionsIfNeedInteractor(get(), get()) }
    factory { DeleteSessionByIdInteractor(get()) }
}

fun ComponentFactory.createSessionsComponent(
    componentContext: ComponentContext,
    onOutput: (SessionsComponent.Output) -> Unit,
): SessionsComponent {
    return RealSessionsComponent(componentContext, onOutput, get(), get(), get(), get(), get())
}

fun createSessionComponent(
    componentContext: ComponentContext,
    detailedSession: Session
): SessionComponent {
    return RealSessionComponent(componentContext, detailedSession)
}