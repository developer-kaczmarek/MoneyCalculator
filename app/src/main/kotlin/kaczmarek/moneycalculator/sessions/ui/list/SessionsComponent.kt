package kaczmarek.moneycalculator.sessions.ui.list

import kaczmarek.moneycalculator.sessions.domain.SessionId
import kaczmarek.moneycalculator.sessions.ui.SessionViewData
import me.aartikov.sesame.loading.simple.Loading

interface SessionsComponent {

    val sessionsViewState: Loading.State<List<SessionViewData>>

    val removedSessionId: SessionId?

    fun onRetryClick()

    fun onSessionDeleteClick(sessionId: SessionId)

    fun onSessionDetailsClick(item: SessionViewData.DetailsViewData)
}