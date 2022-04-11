package kaczmarek.moneycalculator.sessions.ui

import me.aartikov.sesame.loading.simple.Loading

interface SessionsComponent {

    val sessionsViewState: Loading.State<List<SessionViewData>>

    fun onRetryClick()
}