package kaczmarek.moneycalculator.sessions.ui.details

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import kaczmarek.moneycalculator.sessions.domain.Session

class RealSessionComponent(
    componentContext: ComponentContext,
    private val session: Session
) : ComponentContext by componentContext, SessionComponent {

    override val detailedSessionViewData by derivedStateOf {
        session.toDetailedSessionViewData()
    }
}