package kaczmarek.moneycalculator.core.external_app_service

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import kaczmarek.moneycalculator.core.error_handling.MatchingAppNotFoundException
import me.aartikov.sesame.localizedstring.LocalizedString

class ExternalAppServiceImpl(private val context: Context) : ExternalAppService {

    override fun openBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ContextCompat.startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            throw MatchingAppNotFoundException(e)
        }
    }

    override fun sendEmail(email: String, theme: LocalizedString) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, theme.resolve(context))
            }
            ContextCompat.startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            throw MatchingAppNotFoundException(e)
        }
    }

    override fun openPageOnGooglePlay() {
        val packageName = context.packageName
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ContextCompat.startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                ContextCompat.startActivity(context, intent, null)
            } catch (e: ActivityNotFoundException) {
                throw MatchingAppNotFoundException(e)
            }
        }
    }
}