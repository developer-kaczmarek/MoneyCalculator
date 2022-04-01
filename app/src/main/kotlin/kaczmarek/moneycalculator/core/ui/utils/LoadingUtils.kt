package kaczmarek.moneycalculator.core.ui.utils

import kaczmarek.moneycalculator.core.ui.error_handling.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import me.aartikov.sesame.loading.simple.Loading
import me.aartikov.sesame.loading.simple.handleErrors

fun <T : Any> Loading<T>.handleErrors(
    scope: CoroutineScope,
    errorHandler: ErrorHandler,
    onErrorHandled: ((e: Throwable) -> Unit)? = null
): Job {
    return handleErrors(scope) {
        errorHandler.handleError(it.throwable)
        onErrorHandled?.invoke(it.throwable)
    }
}