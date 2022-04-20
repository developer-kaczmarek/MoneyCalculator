package kaczmarek.moneycalculator.sessions.ui.list

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.error_handling.safeLaunch
import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.utils.componentCoroutineScope
import kaczmarek.moneycalculator.core.utils.handleErrors
import kaczmarek.moneycalculator.core.utils.toComposeState
import kaczmarek.moneycalculator.sessions.domain.DeleteOldSessionsIfNeedInteractor
import kaczmarek.moneycalculator.sessions.domain.DeleteSessionByIdInteractor
import kaczmarek.moneycalculator.sessions.domain.GetSessionsInteractor
import kaczmarek.moneycalculator.sessions.domain.SessionId
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh
import me.aartikov.sesame.localizedstring.LocalizedString

class RealSessionsComponent(
    componentContext: ComponentContext,
    private val onOutput: (SessionsComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    private val messageService: MessageService,
    getSessionsInteractor: GetSessionsInteractor,
    deleteOldSessionsIfNeedInteractor: DeleteOldSessionsIfNeedInteractor,
    private val deleteSessionByIdInteractor: DeleteSessionByIdInteractor
) : ComponentContext by componentContext, SessionsComponent {

    private val coroutineScope = componentCoroutineScope()

    private val sessionsLoading = OrdinaryLoading(
        coroutineScope,
        load = { getSessionsInteractor.execute() }
    )

    private val sessionsState by sessionsLoading.stateFlow.toComposeState(coroutineScope)

    override var removedSessionId by mutableStateOf<SessionId?>(null)

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

    override fun onSessionDeleteClick(sessionId: SessionId) {
        removedSessionId?.let { deleteSessionById(it) }
        removedSessionId = sessionId
        messageService.showMessage(
            MessageData(
                text = LocalizedString.resource(R.string.sessions_session_delete),
                timerVisible = true,
                buttonLabel = LocalizedString.resource(R.string.sessions_cancel),
                onButtonClick = { removedSessionId = null },
                onDismiss = { deleteSessionById(sessionId) }
            )
        )
    }

    override fun onSessionDetailsClick(item: SessionViewData.DetailsViewData) {
        onOutput(SessionsComponent.Output.DetailedSessionRequested(item.output))
    }

    private fun deleteSessionById(id: SessionId) {
        coroutineScope.safeLaunch(errorHandler) {
            deleteSessionByIdInteractor.execute(id)
            sessionsLoading.refresh()
        }
    }
}