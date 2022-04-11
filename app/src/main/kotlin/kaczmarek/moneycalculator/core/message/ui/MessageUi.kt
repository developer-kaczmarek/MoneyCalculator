package kaczmarek.moneycalculator.core.message.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kaczmarek.moneycalculator.core.message.domain.MessageData
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.utils.LocalMessageOffsets
import kaczmarek.moneycalculator.core.utils.resolve
import me.aartikov.sesame.localizedstring.LocalizedString

@Composable
fun MessageUi(
    component: MessageComponent,
    modifier: Modifier = Modifier,
) {
    val additionalBottomPadding = with(LocalDensity.current) {
        LocalMessageOffsets.current.values.maxOrNull()?.toDp() ?: 0.dp
    }
    Box(modifier = modifier.fillMaxSize()) {
        component.visibleMessageData?.let {
            MessagePopup(
                messageData = it,
                bottomPadding = 16.dp + additionalBottomPadding,
                onAction = component::onActionClick
            )
        }
    }
}

@Composable
fun MessagePopup(
    messageData: MessageData,
    onAction: () -> Unit,
    bottomPadding: Dp
) {
    Popup(
        alignment = Alignment.BottomCenter,
        properties = PopupProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 3.dp,
            modifier = Modifier
                .padding(bottom = bottomPadding, start = 8.dp, end = 8.dp)
                .wrapContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(vertical = 13.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                messageData.iconRes?.let {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = messageData.text.resolve(),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
                messageData.actionTitle?.let {
                    MessageButton(
                        text = it.resolve(),
                        onClick = onAction
                    )
                }
            }
        }
    }
}

@Composable
fun MessageButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2
        )
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

    override val visibleMessageData = MessageData(LocalizedString.raw("Message"))

    override fun onActionClick() = Unit
}