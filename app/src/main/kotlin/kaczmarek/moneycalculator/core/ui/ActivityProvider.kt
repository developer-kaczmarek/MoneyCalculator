package kaczmarek.moneycalculator.core.ui

import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent

class ActivityProvider : KoinComponent {

    private val activityStateFlow = MutableStateFlow<FragmentActivity?>(null)

    val activity: FragmentActivity? get() = activityStateFlow.value

    fun attachActivity(activity: FragmentActivity) {
        activityStateFlow.value = activity
    }

    fun detachActivity() {
        activityStateFlow.value = null
    }

    suspend fun awaitActivity(): FragmentActivity {
        return activityStateFlow.filterNotNull().first()
    }
}