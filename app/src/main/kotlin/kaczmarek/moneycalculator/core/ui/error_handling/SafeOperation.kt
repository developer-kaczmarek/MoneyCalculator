package kaczmarek.moneycalculator.core.ui.error_handling

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun safeRun(
    errorHandler: ErrorHandler,
    block: () -> Unit
) {
    try {
        block()
    } catch (e: Exception) {
        errorHandler.handleError(e)
    }
}

fun CoroutineScope.safeLaunch(
    errorHandler: ErrorHandler,
    block: suspend () -> Unit
): Job {
    return launch {
        try {
            block()
        } catch (e: CancellationException) {
            // do nothing
        } catch (e: Exception) {
            errorHandler.handleError(e)
        }
    }
}