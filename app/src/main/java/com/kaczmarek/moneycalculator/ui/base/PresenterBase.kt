package com.kaczmarek.moneycalculator.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import moxy.MvpPresenter
import kotlin.coroutines.CoroutineContext

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
open class PresenterBase<View : ViewBase> : MvpPresenter<View>(), CoroutineScope {

    private val parentJob = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + parentJob

    override fun onDestroy() {
        parentJob.cancel()
    }

}