package kaczmarek.moneycalculator.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.ui.base.FragmentBase
import kaczmarek.moneycalculator.ui.base.ViewBase
import kaczmarek.moneycalculator.ui.main.BackStackChangeListenerMain
import kaczmarek.moneycalculator.utils.ExternalNavigation
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Angelina Podbolotova on 19.10.2019.
 */
class FragmentSettingsLicenses : FragmentBase(),
    ViewLicensesOverview {

    private var navigationListener: ExternalNavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as? ExternalNavigation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_licenses, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
            (activity as? BackStackChangeListenerMain)?.onBackStackChange(this)
        }

    }

    override fun onStart() {
        super.onStart()
        setTitle(R.string.activity_main_title_licenses_item)
        showBackArrowButton()
    }

    override fun showMessage(message: String) {
        this.toast(message)
    }

    companion object {
        const val TAG = "FragmentSettingsLicenses"
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
interface ViewLicensesOverview : ViewBase {
    fun showMessage(message: String)
}