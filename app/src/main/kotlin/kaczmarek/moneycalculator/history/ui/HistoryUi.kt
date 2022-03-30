package kaczmarek.moneycalculator.history.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.ui.theme.AppTheme

@Composable
fun HistoryUi(
    component: HistoryComponent,
    modifier: Modifier = Modifier
) {
    Text(text = "History")
}

@Preview(showSystemUi = true)
@Composable
fun HistoryUiPreview() {
    AppTheme {
        HistoryUi(FakeHistoryUiComponent())
    }
}

class FakeHistoryUiComponent : HistoryComponent