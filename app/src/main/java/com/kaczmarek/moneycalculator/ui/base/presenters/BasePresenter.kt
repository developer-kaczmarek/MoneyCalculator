package com.kaczmarek.moneycalculator.ui.base.presenters

import com.kaczmarek.moneycalculator.ui.base.views.BaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import moxy.MvpPresenter
import kotlin.coroutines.CoroutineContext

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
open class BasePresenter<View : BaseView> : MvpPresenter<View>(), CoroutineScope {

    private val parentJob = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + parentJob

    override fun onDestroy() {
        parentJob.cancel()
    }

}