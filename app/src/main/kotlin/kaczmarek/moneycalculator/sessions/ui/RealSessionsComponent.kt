package kaczmarek.moneycalculator.sessions.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import kaczmarek.moneycalculator.core.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.error_handling.safeLaunch
import kaczmarek.moneycalculator.core.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.utils.handleErrors
import kaczmarek.moneycalculator.core.utils.toComposeState
import kaczmarek.moneycalculator.sessions.domain.DeleteOldSessionsIfNeedInteractor
import kaczmarek.moneycalculator.sessions.domain.GetSessionsInteractor
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh

class RealSessionsComponent(
    componentContext: ComponentContext,
    private val errorHandler: ErrorHandler,
    getSessionsInteractor: GetSessionsInteractor,
    deleteOldSessionsIfNeedInteractor: DeleteOldSessionsIfNeedInteractor
) : ComponentContext by componentContext, SessionsComponent {

    private val coroutineScope = componentCoroutineScope()

    private val sessionsLoading = OrdinaryLoading(
        coroutineScope,
        load = { getSessionsInteractor.execute() }
    )

    private val sessionsState by sessionsLoading.stateFlow.toComposeState(coroutineScope)

    override val sessionsViewState by derivedStateOf {
        sessionsState.mapData { sessions ->
            val history = arrayListOf<SessionViewData>()
            sessions.reversed().groupBy { it.date }.forEach { session ->
                history.add(session.key.toViewData())
                session.value.sortedByDescending { it.time }.forEach {
                    history.add(it.toViewData())
                }
            }
            history
        }
    }

    init {
        lifecycle.doOnCreate {
            coroutineScope.safeLaunch(errorHandler) {
                deleteOldSessionsIfNeedInteractor.execute()
            }
            sessionsLoading.handleErrors(coroutineScope, errorHandler)
        }
        lifecycle.doOnStart {
            sessionsLoading.refresh()
        }
    }

    override fun onRetryClick() {
        sessionsLoading.refresh()
    }
}