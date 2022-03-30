package kaczmarek.moneycalculator.calculator.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kaczmarek.moneycalculator.core.ui.theme.AppTheme

@Composable
fun CalculatorUi(
    component: CalculatorComponent,
    modifier: Modifier = Modifier
) {
    Text(text = "Calculator")
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorUiPreview() {
    AppTheme {
        CalculatorUi(FakeCalculatorComponent())
    }
}

class FakeCalculatorComponent : CalculatorComponent