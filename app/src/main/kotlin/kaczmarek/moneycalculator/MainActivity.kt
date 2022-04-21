package kaczmarek.moneycalculator

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.arkivanov.essenty.lifecycle.asEssentyLifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import androidx.fragment.app.FragmentActivity
import com.arkivanov.decompose.defaultComponentContext
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.koin
import kaczmarek.moneycalculator.core.ActivityProvider
import kaczmarek.moneycalculator.root.createRootComponent
import kaczmarek.moneycalculator.root.ui.RootUi

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityProvider = application.koin.get<ActivityProvider>()
        activityProvider.attachActivity(this)
        lifecycle.asEssentyLifecycle().doOnDestroy {
            activityProvider.detachActivity()
        }

        val componentFactory = application.koin.get<ComponentFactory>()
        val rootComponent = componentFactory.createRootComponent(defaultComponentContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RootUi(rootComponent)
        }
    }
}