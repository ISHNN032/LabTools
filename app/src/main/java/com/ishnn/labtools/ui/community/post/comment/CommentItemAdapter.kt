package com.ishnn.labtools.ui.community.post.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.model.CommentItem
import com.ishnn.labtools.ui.community.post.PostManager
import java.text.SimpleDateFormat
import java.util.*

class CommentItemAdapter(
    var items: MutableList<CommentItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvNotice: TextView
        val tvTitle: TextView
        val tvNickname: TextView
        val tvTime: TextView
        val tvCommentCount: TextView
        val tvFavorateCount: TextView
        val ivImage: ImageView
        init {
            with(item) {
                tvNotice = findViewById(R.id.post_item_tv_notice)
                tvTitle = findViewById(R.id.post_item_tv_title)
                tvNickname = findViewById(R.id.post_item_tv_nickname)
                tvTime = findViewById(R.id.post_item_tv_time)
                tvCommentCount = findViewById(R.id.post_item_tv_comment)
                tvFavorateCount = findViewById(R.id.post_item_tv_favorite)
                ivImage = findViewById(R.id.post_item_iv_image)
            }
        }
    }

    interface ClickListener {
        fun onClickListener(pos: Int, aView: View)
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem: CommentItem, pos: Int){
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun refreshData(){
        items.clear()
        addData()
    }

    fun addData(){
        val callbackAll: (List<CommentItem>) -> Unit = { data ->
            items.addAll(data)
            notifyDataSetChanged()
        }
        PostManager.getPostComments(callback = callbackAll)
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if(item.replyOfId.isNullOrEmpty()){

        }else{

        }

        val callback: (String?) -> Unit = { name ->
            (holder as ViewHolder).tvNickname.text = name
        }
        PostManager.getUserName(item.user!!, callback = callback)



        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        (holder as ViewHolder).tvTime.text = time.format(item.time!!)
    }
}