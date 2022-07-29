package kaczmarek.moneycalculator.calculator.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.theme.AppTheme
import kaczmarek.moneycalculator.core.utils.resolve
import kaczmarek.moneycalculator.core.widgets.LceWidget
import me.aartikov.sesame.loading.simple.Loading

@Composable
fun CalculatorUi(
    component: CalculatorComponent,
    modifier: Modifier = Modifier
) {
    LceWidget(
        data = component.calculatingSessionViewState,
        onRetryClick = {}
    ) {
        Box(modifier = modifier) {
            val lazyListState = rememberLazyListState()

            if (it.isKeepScreenOn) {
                KeepScreenOn()
            }
            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            )

            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                TotalBoard(
                    totalAmount = it.totalAmount.resolve(),
                    totalCount = it.totalCount.resolve()
                )

                LaunchedEffect(component.selectedBanknoteIndex) {
                    lazyListState.animateScrollToItem(component.selectedBanknoteIndex)
                }

                LazyRow(
                    contentPadding = PaddingValues(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    state = lazyListState
                ) {
                    itemsIndexed(it.banknotes) { index: Int, item: DetailedBanknoteViewData ->
                        BanknoteCard(
                            item = item,
                            isSelected = index == component.selectedBanknoteIndex,
                            onBanknoteCardClick = component::onBanknoteCardClick
                        )
                    }
                }

                CalculatorKeyboard(
                    isClassicKeyboard = it.isClassicKeyboard,
                    isForwardButtonEnabled = it.isForwardButtonEnabled,
                    isNextButtonEnabled = it.isNextButtonEnabled,
                    onDigitClick = component::onDigitClick,
                    onBackspaceClick = component::onBackspaceClick,
                    onSaveClick = component::onSaveClick,
                    onForwardClick = component::onForwardClick,
                    onNextClick = component::onNextClick,
                    onCountingDetailsClick = component::onCountingDetailsClick,
                    onBackspaceLongClick = component::onBackspaceLongClick
                )
            }
        }
    }
}

@Composable
fun TotalBoard(
    totalAmount: String,
    totalCount: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.common_total),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface
        )
        Text(
            text = totalAmount,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = totalCount,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun BanknoteCard(
    item: DetailedBanknoteViewData,
    isSelected: Boolean,
    onBanknoteCardClick: (DetailedBanknoteViewData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = modifier
                .width(140.dp)
                .clickable { onBanknoteCardClick(item) },
            border = if (isSelected) {
                BorderStroke(3.dp, Color(item.borderColor))
            } else {
                BorderStroke(1.dp, MaterialTheme.colors.onBackground)
            },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = item.name.resolve(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .padding(vertical = 10.dp)
                                .padding(start = 10.dp),
                            text = "шт",
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .padding(start = 10.dp),
                            text = "₽",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.surface)
                                .padding(10.dp),
                            text = item.count,
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = item.amount,
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) {
                        Color(item.borderColor)
                    } else {
                        MaterialTheme.colors.background
                    }
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorKeyboard(
    isClassicKeyboard: Boolean,
    isForwardButtonEnabled: Boolean,
    isNextButtonEnabled: Boolean,
    onDigitClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onBackspaceLongClick: () -> Unit,
    onSaveClick: () -> Unit,
    onForwardClick: () -> Unit,
    onNextClick: () -> Unit,
    onCountingDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(36.dp)) {
            KeyboardColumn {
                IconButton(
                    onClick = onForwardClick,
                    enabled = isForwardButtonEnabled,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(18.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_36_left_arrow),
                        contentDescription = null
                    )
                }

                val digits = listOf("1", "4", "7")
                digits
                    .reversedIfNeed(shouldReversed = !isClassicKeyboard)
                    .forEach { DigitButton(text = it, onClick = { onDigitClick(it) }) }

                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(18.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_36_save),
                        contentDescription = null
                    )
                }
            }
            KeyboardColumn {
                IconButton(
                    onClick = onCountingDetailsClick,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(18.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_36_list),
                        contentDescription = null
                    )
                }

                val digits = listOf("2", "5", "8")
                digits
                    .reversedIfNeed(shouldReversed = !isClassicKeyboard)
                    .forEach { DigitButton(text = it, onClick = { onDigitClick(it) }) }

                DigitButton(text = "0", onClick = { onDigitClick("0") })
            }
            KeyboardColumn {
                IconButton(
                    onClick = onNextClick,
                    enabled = isNextButtonEnabled,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(18.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_36_right_arrow),
                        contentDescription = null
                    )
                }

                val digits = listOf("3", "6", "9")
                digits
                    .reversedIfNeed(shouldReversed = !isClassicKeyboard)
                    .forEach { DigitButton(text = it, onClick = { onDigitClick(it) }) }

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(18.dp)
                        .combinedClickable(
                            onClick = onBackspaceClick,
                            onLongClick = onBackspaceLongClick,
                            role = Role.Button,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_36_backspace),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun KeyboardColumn(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround) {
        content()
    }
}

@Composable
fun DigitButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(64.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onBackground
        ),
        elevation = null
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

@Composable
fun KeepScreenOn() {
    val currentView = LocalView.current
    DisposableEffect(Unit) {
        currentView.keepScreenOn = true
        onDispose {
            currentView.keepScreenOn = false
        }
    }
}

private fun List<String>.reversedIfNeed(shouldReversed: Boolean): List<String> {
    return if (shouldReversed) this.asReversed() else this
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorUiPreview() {
    AppTheme {
        CalculatorUi(FakeCalculatorComponent())
    }
}

class FakeCalculatorComponent : CalculatorComponent {

    override val calculatingSessionViewState: Loading.State<CalculatingSessionViewData>
        get() = Loading.State.Data(CalculatingSessionViewData.MOCK)

    override val selectedBanknoteIndex: Int = 0

    override fun onDigitClick(digit: String) = Unit

    override fun onBackspaceClick() = Unit

    override fun onBanknoteCardClick(banknote: DetailedBanknoteViewData) = Unit

    override fun onForwardClick() = Unit

    override fun onNextClick() = Unit

    override fun onSaveClick() = Unit

    override fun onCountingDetailsClick() = Unit

    override fun onBackspaceLongClick() = Unit
}