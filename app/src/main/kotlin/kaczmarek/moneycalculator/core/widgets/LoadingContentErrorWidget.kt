package kaczmarek.moneycalculator.core.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.error_handling.errorMessage
import kaczmarek.moneycalculator.core.utils.resolve
import me.aartikov.sesame.loading.simple.Loading

@Composable
fun <T> LceWidget(
    data: Loading.State<T>,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    content: @Composable (data: T) -> Unit
) {
    when (data) {
        is Loading.State.Error -> ErrorWidget(
            modifier = modifier,
            throwable = data.throwable,
            onRetry = onRetryClick
        )
        is Loading.State.Loading -> Unit
        is Loading.State.Empty -> emptyContent?.invoke()
        is Loading.State.Data -> content(data.data)
    }
}

@Composable
private fun ErrorWidget(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = throwable.errorMessage.resolve(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1
            )
            Button(
                onClick = onRetry,
                enabled = true,
                modifier = modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.surface,
                    disabledContentColor = MaterialTheme.colors.onSurface
                ),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.common_retry),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}