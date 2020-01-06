package kaczmarek.moneycalculator.ui.history

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kaczmarek.moneycalculator.R
import kotlinx.android.synthetic.main.rv_session_item.view.*


class SwipeCallbackHistory(
    dragDirs: Int,
    swipeDirs: Int,
    private val listenerDeleteItemHistory: ListenerDeleteItemHistory
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listenerDeleteItemHistory.onSwipe(viewHolder.adapterPosition)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder.itemViewType == R.layout.rv_session_item) {
            ItemTouchHelper.Callback.makeMovementFlags(
                0,
                ItemTouchHelper.START or ItemTouchHelper.LEFT
            )
        } else {
            ItemTouchHelper.Callback.makeMovementFlags(0, 0)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            val foregroundView = it.itemView.ll_foreground
            ItemTouchHelper.Callback.getDefaultUIUtil()
                .onSelected(foregroundView)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView = viewHolder.itemView.ll_foreground
        ItemTouchHelper.Callback.getDefaultUIUtil()
            .clearView(foregroundView)
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView = viewHolder?.itemView?.ll_foreground
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
            c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive
        )
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView

         val foregroundView = itemView.ll_foreground

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive)
    }


}