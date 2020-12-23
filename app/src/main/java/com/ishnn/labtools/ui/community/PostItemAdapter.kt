package com.ishnn.labtools.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.util.adapter.OnMoveAndSwipedListener
import com.rnnzzo.uxdesign.model.RvItem
import java.util.*

val TYPE_HEADER = 1
val TYPE_ITEM = 2
val TYPE_LOADER = 3

class PostItemAdapter(
    var items: MutableList<RvItem>,
    val clickListener: PostFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnMoveAndSwipedListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onItemMove(start: Int, end: Int): Boolean {
        Collections.swap(items, start, end)
        notifyItemMoved(start, end)
        return true
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val ivIcon: ImageView
        private val tvTitle: TextView
        private val tvDescription: TextView

        init {
            with(item) {
                ivIcon = findViewById(R.id.iv_rv_appicon)
                tvTitle = findViewById(R.id.tv_title)
                tvDescription = findViewById(R.id.tv_description)
            }
        }
    }

    interface ClickListener {
        fun onClickListener(pos: Int, aView: View)
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem:RvItem, pos: Int){
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun addData(extraItems: List<RvItem>){
        items.addAll(extraItems)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}