package kaczmarek.moneycalculator.settings.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.ui.theme.AppTheme

@Composable
fun SettingsUi(
    component: SettingsComponent,
    modifier: Modifier = Modifier
) {
    Text(text = "Settings")
}

@Preview(showSystemUi = true)
@Composable
fun SettingsUiPreview() {
    AppTheme {
        SettingsUi(FakeSettingsUiComponent())
    }
}

class FakeSettingsUiComponent : SettingsComponent