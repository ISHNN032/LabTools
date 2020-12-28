package com.ishnn.labtools.ui.community.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.ui.community.CommunityPosts
import java.text.SimpleDateFormat
import java.util.*

val TYPE_ITEM = 2
val TYPE_LOADER = 3

class PostItemAdapter(
    var posts: CommunityPosts? = null,
    var items: MutableList<PostItem>,
    val fragment: PostFragment
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

    fun addItem(extraItem: PostItem, pos: Int){
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun refreshData(){
        items.clear()
        addData()
    }

    fun addData(){
        val callbackAll: (List<PostItem>) -> Unit = { data ->
            items.addAll(data)
            notifyDataSetChanged()
        }
        val callback: (PostItem) -> Unit = { data ->
            items.add(data)
            notifyDataSetChanged()
        }
        when(fragment.requireArguments().get("Tag").toString()){
            "전체글" ->{
                posts!!.getPosts(callback = callbackAll)
            }
            "즐겨찾기"->{
                posts!!.getFavorites(callback = callback)
            }
            "전체공지"->{
                posts!!.getNotices(callback = callbackAll)
            }
        }
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(item.notice!!){
            (holder as ViewHolder).tvNotice.visibility = View.VISIBLE
        }else{
            (holder as ViewHolder).tvNotice.visibility = View.GONE
        }
        (holder as ViewHolder).tvTitle.text = item.title

        val callback: (String?) -> Unit = { name ->
            (holder as ViewHolder).tvNickname.text = name
        }
        posts!!.getUserName(item.user!!, callback = callback)


        val date = SimpleDateFormat("yyyy-MM-dd")
        val time = SimpleDateFormat("HH:mm")
        if(date.format(item.time!!) == date.format(Date())){
            (holder as ViewHolder).tvTime.text = time.format(item.time)
        }else{
            (holder as ViewHolder).tvTime.text = date.format(item.time)
        }

        (holder as ViewHolder).tvCommentCount.text = item.commentCount.toString()
        (holder as ViewHolder).tvFavorateCount.text = item.favoriteCount.toString()
        //(holder as ViewHolder).ivImage.setImageBitmap(item.imageUrl)
    }
}