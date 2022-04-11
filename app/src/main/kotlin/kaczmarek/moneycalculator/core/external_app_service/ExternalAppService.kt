package kaczmarek.moneycalculator.core.external_app_service

import me.aartikov.sesame.localizedstring.LocalizedString

interface ExternalAppService {

    fun openBrowser(url: String)

    fun sendEmail(email: String, theme: LocalizedString = LocalizedString.empty())

    fun openPageOnGooglePlay()
}