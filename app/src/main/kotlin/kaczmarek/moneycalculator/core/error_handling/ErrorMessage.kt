package kaczmarek.moneycalculator.core.error_handling

import kaczmarek.moneycalculator.R
import me.aartikov.sesame.localizedstring.LocalizedString

val Throwable.errorMessage: LocalizedString
    get() = when (this) {

        is MatchingAppNotFoundException -> LocalizedString.resource(R.string.settings_app_for_intent_not_found)

        else -> {
            val description = this.message
            LocalizedString.resource(R.string.common_error, description ?: "")
        }
    }