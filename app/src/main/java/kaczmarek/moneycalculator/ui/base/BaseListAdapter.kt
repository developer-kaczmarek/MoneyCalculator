package kaczmarek.moneycalculator.ui.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<D : ItemBase, VH : BaseViewHolder>
@JvmOverloads
constructor(differ: DiffUtil.ItemCallback<D> = DiffCallback()) : ListAdapter<D, VH>(differ) {

    var clicklistener: BaseClickListener? = null
    var checkChangeListener: BaseCheckChangeListener? = null

    fun update(list: List<D>) {
        this.submitList(ArrayList(list))
    }

    @SuppressLint("DiffUtilEquals")
    private class DiffCallback<D : ItemBase> : DiffUtil.ItemCallback<D>() {

        override fun areContentsTheSame(oldItem: D, newItem: D): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: D, newItem: D): Boolean =
            oldItem.getItemId() == newItem.getItemId()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int) = getItem(position).itemViewType
}

interface DiffComparable {
    fun getItemId(): Int
}

interface BaseClickListener {
    fun onClick(view: View, position: Int)
}

interface BaseCheckChangeListener {
    fun onChange(view: View, position: Int, isCheck: Boolean)
}