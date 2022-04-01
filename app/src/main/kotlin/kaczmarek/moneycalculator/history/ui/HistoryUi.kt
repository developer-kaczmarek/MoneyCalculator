package kaczmarek.moneycalculator.history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.ui.theme.AppTheme
import kaczmarek.moneycalculator.core.ui.widgets.Header

@Composable
fun HistoryUi(
    component: HistoryComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        Header(text = "История")
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistoryUiPreview() {
    AppTheme {
        HistoryUi(FakeHistoryUiComponent())
    }
}

class FakeHistoryUiComponent : HistoryComponent