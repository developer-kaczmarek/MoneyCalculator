package kaczmarek.moneycalculator.core.ui.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun <T : Any> createFakeRouterState(instance: T): RouterState<*, T> {
    return RouterState(
        activeChild = Child.Created(
            configuration = "<fake>",
            instance = instance
        )
    )
}

fun LifecycleOwner.componentCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        lifecycle.doOnDestroy {
            scope.cancel()
        }
    } else {
        scope.cancel()
    }

    return scope
}

fun <T : Any> Value<T>.toComposeState(lifecycle: Lifecycle): State<T> {
    val state: MutableState<T> = mutableStateOf(this.value)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        val observer: ValueObserver<T> = { state.value = it }
        subscribe(observer)
        lifecycle.doOnDestroy {
            unsubscribe(observer)
        }
    }

    return state
}

val <C : Any, T : Any> RouterState<C, T>.currentInstance get() = activeChild.instance