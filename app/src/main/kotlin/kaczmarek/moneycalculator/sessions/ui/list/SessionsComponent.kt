package kaczmarek.moneycalculator.sessions.ui.list

import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId
import me.aartikov.sesame.loading.simple.Loading

interface SessionsComponent {

    sealed interface Output {
        class DetailedSessionRequested(val session: Session) : Output
    }

    val sessionsViewState: Loading.State<List<SessionViewData>>

    val removedSessionId: SessionId?

    fun onRetryClick()

    fun onSessionDeleteClick(sessionId: SessionId)

    fun onSessionDetailsClick(item: SessionViewData.DetailsViewData)
}