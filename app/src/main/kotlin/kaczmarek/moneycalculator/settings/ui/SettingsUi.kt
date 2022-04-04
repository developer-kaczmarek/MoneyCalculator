package kaczmarek.moneycalculator.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.ui.theme.AppTheme
import kaczmarek.moneycalculator.core.ui.utils.resolve
import kaczmarek.moneycalculator.core.ui.widgets.Header
import kaczmarek.moneycalculator.settings.domain.Settings
import me.aartikov.sesame.loading.simple.Loading

@Composable
fun SettingsUi(
    component: SettingsComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Header(text = stringResource(id = R.string.settings_title))

        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            when (val data = component.settingsViewState) {
                is Loading.State.Error -> {
                    println("Error, ${data.throwable}")
                }

                is Loading.State.Loading -> {
                    println("Loading")
                }

                is Loading.State.Empty -> {
                    println("Empty")
                }

                is Loading.State.Data -> {
                    BanknotesSettingsBlock(
                        items = data.data.banknotes,
                        onChoiceChanged = component::onBanknoteClick
                    )

                    HistorySettingsBlock(
                        items = data.data.historyStoragePeriods,
                        onChoiceChanged = component::onHistoryStoragePeriodClick
                    )

                    KeyboardSettingsBlock(
                        items = data.data.keyboardLayoutTypes,
                        onChoiceChanged = component::onKeyboardLayoutTypeClick
                    )

                    DisplaySettingsBlock(
                        checked = data.data.isKeepScreenOn,
                        onKeepScreenOnClick = component::onKeepScreenOnClick
                    )

                    ThemeSettingsBlock(
                        items = data.data.themeTypes,
                        onChoiceChanged = component::onThemeTypeClick
                    )

                    OtherSettingsBlock(
                        onGithubClick = component::onGithubClick,
                        onPrivacyPolicyClick = component::onPrivacyPolicyClick,
                        onContactDeveloperClick = component::onContactDeveloperClick,
                        onGooglePlayClick = component::onGooglePlayClick
                    )

                    Text(
                        text = data.data.versionDescription.resolve(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TextCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colors.background,
                uncheckedColor = MaterialTheme.colors.onBackground
            )
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TextRadioButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                unselectedColor = MaterialTheme.colors.onBackground
            ),
            enabled = true
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = text,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TextSwitchButton(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primaryVariant,
                uncheckedTrackColor = MaterialTheme.colors.surface,
                uncheckedThumbColor = MaterialTheme.colors.onSurface,
                uncheckedTrackAlpha = 1f
            )
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun BanknotesSettingsBlock(
    items: List<SettingsChoice<Banknote>>,
    onChoiceChanged: (Banknote) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_banknotes),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 10.dp)
    )
    items.forEach { item ->
        TextCheckBox(
            text = item.title.resolve(),
            checked = item.isChosen,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChoiceChanged(item.output) }
        )
    }
}

@Composable
fun HistorySettingsBlock(
    items: List<SettingsChoice<Settings.HistoryStoragePeriod>>,
    onChoiceChanged: (Settings.HistoryStoragePeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_save_history),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    items.forEach { item ->
        TextRadioButton(
            text = item.title.resolve(),
            selected = item.isChosen,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChoiceChanged(item.output) }
        )
    }
}

@Composable
fun KeyboardSettingsBlock(
    items: List<SettingsChoice<Settings.KeyboardLayoutType>>,
    onChoiceChanged: (Settings.KeyboardLayoutType) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_keyboard_layout),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    items.forEach { item ->
        TextRadioButton(
            text = item.title.resolve(),
            selected = item.isChosen,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChoiceChanged(item.output) }
        )
    }
}

@Composable
fun ThemeSettingsBlock(
    items: List<SettingsChoice<Settings.ThemeType>>,
    onChoiceChanged: (Settings.ThemeType) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_theme),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    items.forEach { item ->
        TextRadioButton(
            text = item.title.resolve(),
            selected = item.isChosen,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChoiceChanged(item.output) }
        )
    }
}

@Composable
fun DisplaySettingsBlock(
    checked: Boolean,
    onKeepScreenOnClick: (oldCheckedValue: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_display),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    TextSwitchButton(
        text = stringResource(id = R.string.settings_display_off),
        checked = checked,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onKeepScreenOnClick(checked) }
    )
}

@Composable
fun OtherSettingsBlock(
    onGithubClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactDeveloperClick: () -> Unit,
    onGooglePlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.settings_other),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Text(
        text = stringResource(id = R.string.settings_github),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onGithubClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Text(
        text = stringResource(id = R.string.settings_privacy_policy),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPrivacyPolicyClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Text(
        text = stringResource(id = R.string.settings_contact_developer),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onContactDeveloperClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Text(
        text = stringResource(id = R.string.settings_rate_app),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onGooglePlayClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingsUiPreview() {
    AppTheme {
        SettingsUi(FakeSettingsUiComponent())
    }
}

class FakeSettingsUiComponent : SettingsComponent {

    override val settingsViewState = Loading.State.Data(SettingsViewData.MOCK)

    override fun onBanknoteClick(banknote: Banknote) = Unit

    override fun onKeyboardLayoutTypeClick(keyboardLayoutType: Settings.KeyboardLayoutType) = Unit

    override fun onHistoryStoragePeriodClick(
        historyStoragePeriod: Settings.HistoryStoragePeriod
    ) = Unit

    override fun onContactDeveloperClick() = Unit

    override fun onPrivacyPolicyClick() = Unit

    override fun onGithubClick() = Unit

    override fun onGooglePlayClick() = Unit

    override fun onKeepScreenOnClick(oldCheckedValue: Boolean) = Unit

    override fun onThemeTypeClick(themeType: Settings.ThemeType) = Unit
}