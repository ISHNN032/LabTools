package com.ishnn.labtools.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.util.adapter.OnMoveAndSwipedListener
import com.rnnzzo.uxdesign.model.RvItem
import java.util.*

val TYPE_ITEM = 2
val TYPE_LOADER = 3

class PostItemAdapter(
    var items: MutableList<Post>,
    val clickListener: PostFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnDataReceivedInterface {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val ivImage: ImageView
        val tvTitle: TextView
        val tvNickname: TextView

        init {
            with(item) {
                ivImage = findViewById(R.id.post_item_iv_image)
                tvTitle = findViewById(R.id.post_item_tv_title)
                tvNickname = findViewById(R.id.post_item_tv_nickname)
            }
        }
    }

    interface ClickListener {
        fun onClickListener(pos: Int, aView: View)
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem:Post, pos: Int){
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun addData(extraItems: Posts){
        extraItems.getPosts(callback = this)
    }

    override fun onDataReceived(data: List<Post>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as ViewHolder).tvTitle.text = item.title
    }
}