package kaczmarek.moneycalculator.core.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.core.ui.theme.AppTheme

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    AppTheme {
        Header(text = "Header")
    }
}