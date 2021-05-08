package kaczmarek.moneycalculator.utils.components.progresssnackbar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.google.android.material.snackbar.BaseTransientBottomBar
import kaczmarek.moneycalculator.R
import kaczmarek.moneycalculator.utils.findSuitableParent

class ProgressSnackBar(
    @NonNull parent: ViewGroup,
    @NonNull customView: ProgressSnackBarView
) : BaseTransientBottomBar<ProgressSnackBar>(parent, customView, customView) {

    init {
        animationMode = ANIMATION_MODE_FADE
    }

    override fun addCallback(callback: BaseCallback<ProgressSnackBar>?): ProgressSnackBar {
        return super.addCallback(callback)
    }

    companion object {

        fun make(@NonNull view: View, action: () -> Unit = {}): ProgressSnackBar? {
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            return try {
                val customView = LayoutInflater.from(view.context).inflate(
                    R.layout.layout_progress_snackbar,
                    parent,
                    false
                ) as ProgressSnackBarView

                customView.startFiveSecondsProgress(action)

                ProgressSnackBar(parent, customView).setDuration(5000)
            } catch (e: Exception) {
                Log.v("exception ", e.message ?: "")
                null
            }
        }
    }
}