package com.kaczmarek.moneycalculator.ui.main.presenters

import com.kaczmarek.moneycalculator.ui.base.presenters.BasePresenter
import com.kaczmarek.moneycalculator.ui.main.views.MainView
import moxy.InjectViewState
import java.util.*

/**
 * Created by Angelina Podbolotova on 05.10.2019.
 */
@InjectViewState
class MainPresenter : BasePresenter<MainView>(){
    private val fragmentStack = Stack<Int>()

    override fun onFirstViewAttach() {
        viewState.onFirstOpen()
    }

    /**
     * Добавляет идентификтор фрагмента в [Stack] для осуществления корректной навигации
     * и предотвращения множественного открытия
     */
    fun addFragmentToStack(fragmentId: Int) {
        if (fragmentStack.contains(fragmentId)) {
            fragmentStack.remove(fragmentId)
        }
        fragmentStack.add(fragmentId)
    }


}