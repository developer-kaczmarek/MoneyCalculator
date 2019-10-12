package com.kaczmarek.moneycalculator.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View

@Suppress("UNUSED")
interface FragmentNavigation {

    fun AppCompatActivity.attachFragment(@IdRes containerId: Int, fragmentInstance: Fragment, tag: String?, isAddToBackStack: Boolean = false, sharedElements: Map<String, View>? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = fragmentInstance
            fragmentTransaction.add(containerId, fragment, tag)
        } else {
            fragmentTransaction.attach(fragment)
        }

        val curFrag = supportFragmentManager.primaryNavigationFragment
        if (fragment != curFrag) {
            if (curFrag != null) {
                fragmentTransaction.detach(curFrag)
            }

            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }

            if (sharedElements != null) {
                for (sharedItem in sharedElements) {
                    fragmentTransaction.addSharedElement(sharedItem.value, sharedItem.key)
                }
            } else {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }

            fragmentTransaction
                .setPrimaryNavigationFragment(fragment)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    fun Fragment.attachFragment(@IdRes containerId: Int, fragmentInstance: Fragment, tag: String?, isAddToBackStack: Boolean = false, sharedElements: Map<String, View>? = null) {
        if (requireActivity() !is AppCompatActivity) return

        (requireActivity() as AppCompatActivity).attachFragment(containerId, fragmentInstance, tag, isAddToBackStack, sharedElements)
    }
}