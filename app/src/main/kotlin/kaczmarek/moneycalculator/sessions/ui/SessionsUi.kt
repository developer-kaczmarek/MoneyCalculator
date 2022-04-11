package kaczmarek.moneycalculator.sessions.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.ui.utils.resolve
import kaczmarek.moneycalculator.core.ui.widgets.Header
import kaczmarek.moneycalculator.core.ui.widgets.LceWidget
import me.aartikov.sesame.localizedstring.LocalizedString
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.ui.theme.AppTheme
import me.aartikov.sesame.loading.simple.Loading

@Composable
fun SessionsUi(
    component: SessionsComponent,
    modifier: Modifier = Modifier
) {
    LceWidget(
        data = component.sessionsViewState,
        onRetryClick = component::onRetryClick,
        emptyContent = { SessionsEmptyPlaceholder() }
    ) { sessions ->
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item { Header(text = stringResource(id = R.string.history_title)) }
            items(sessions) { session ->
                when (session) {
                    is SessionViewData.DateViewData -> SessionsDateTitle(session.value)

                    is SessionViewData.DetailsViewData -> SessionDetails(session)
                }
            }
        }
    }
}

@Composable
fun SessionsEmptyPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Header(text = stringResource(id = R.string.history_title))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(40.dp),
                painter = painterResource(id = R.drawable.img_empty_history),
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(vertical = 20.dp),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                text = stringResource(id = R.string.history_empty_history)
            )
        }
    }
}

@Composable
private fun SessionsDateTitle(
    text: LocalizedString,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 10.dp),
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onSurface,
        text = text.resolve()
    )
}

@Composable
private fun SessionDetails(
    data: SessionViewData.DetailsViewData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = data.time,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier.fillMaxWidth()
            )
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.history_sum_name),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = data.totalAmount.resolve(),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = modifier.fillMaxWidth()
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = stringResource(id = R.string.history_count_name),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = data.totalCount.resolve(),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_36_right_arrow),
            contentDescription = null
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SessionsUiPreview() {
    AppTheme {
        SessionsUi(FakeSessionsComponent())
    }
}

class FakeSessionsComponent : SessionsComponent {
    override val sessionsViewState: Loading.State<List<SessionViewData>>
        get() = TODO("Not yet implemented")

    override fun onRetryClick() = Unit
}