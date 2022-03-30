package kaczmarek.moneycalculator.message.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kaczmarek.moneycalculator.core.ui.utils.LocalMessageOffsets
import kaczmarek.moneycalculator.core.ui.message.MessageData
import kaczmarek.moneycalculator.core.ui.utils.resolve
import kaczmarek.moneycalculator.core.ui.theme.AppTheme
import kaczmarek.moneycalculator.core.ui.widgets.dialog.ShowDialog
import kaczmarek.moneycalculator.core.ui.widgets.dialog.DialogResult
import kotlinx.coroutines.delay
import me.aartikov.sesame.dialog.DialogControl

@Composable
fun MessageUi(
    component: MessageComponent,
    modifier: Modifier = Modifier
) {
    val bottomPadding = with(LocalDensity.current) {
        LocalMessageOffsets.current.values.maxOrNull()?.toDp() ?: 0.dp
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding + 16.dp)
    ) {
        MessageDialog(component.dialogControl)
    }
}

@Composable
fun MessageDialog(dialog: DialogControl<MessageData, DialogResult>) {
    ShowDialog(dialog) { data ->
        Popup(
            alignment = Alignment.BottomCenter,
            onDismissRequest = dialog::dismiss,
            properties = PopupProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 3.dp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(vertical = 13.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    data.iconRes?.let {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = data.text.resolve(),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }

        LaunchedEffect(data) {
            delay(4000L)
            dialog.sendResult(DialogResult.Cancel)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MessageUiPreview() {
    AppTheme {
        MessageUi(FakeMessageComponent())
    }
}

class FakeMessageComponent : MessageComponent {

    override val dialogControl = DialogControl<MessageData, DialogResult>()
}