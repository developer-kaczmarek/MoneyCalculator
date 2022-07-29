package kaczmarek.moneycalculator.sessions.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.utils.dispatchOnBackPressed
import kaczmarek.moneycalculator.core.utils.resolve

@Composable
fun SessionUi(
    component: SessionComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = component.detailedSessionViewData.date.resolve(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )

            val context = LocalContext.current

            IconButton(
                onClick = { dispatchOnBackPressed(context) },
                modifier = Modifier
                    .size(64.dp)
                    .padding(18.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_36_left_arrow),
                    contentDescription = null
                )
            }
        }
        LazyColumn {
            items(component.detailedSessionViewData.banknotes) { banknote ->
                SessionBanknoteItem(banknote)
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Divider()
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        style = MaterialTheme.typography.h1,
                        text = stringResource(id = R.string.common_total),
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = component.detailedSessionViewData.totalAmount.resolve(),
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = component.detailedSessionViewData.totalCount.resolve(),
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun SessionBanknoteItem(
    banknoteViewData: DetailedSessionBanknoteViewData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = banknoteViewData.name.resolve(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = banknoteViewData.count.resolve(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SessionUiPreview() {
    AppTheme {
        SessionUi(FakeSessionComponent())
    }
}

class FakeSessionComponent : SessionComponent {

    override val detailedSessionViewData: DetailedSessionViewData = DetailedSessionViewData.MOCK
}
