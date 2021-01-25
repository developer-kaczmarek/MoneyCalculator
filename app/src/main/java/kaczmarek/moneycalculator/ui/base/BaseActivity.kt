package kaczmarek.moneycalculator.ui.base

import androidx.annotation.LayoutRes
import moxy.MvpAppCompatActivity
import moxy.MvpView

/**
 * Created by Angelina Podbolotova on 14.09.2019.
 */
interface ViewBase : MvpView

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : MvpAppCompatActivity(contentLayoutId), ViewBase

