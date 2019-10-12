package com.kaczmarek.moneycalculator.utils

import androidx.annotation.IdRes

interface ExternalNavigation {
    fun onNavigateTo(@IdRes tabId: Int)
}