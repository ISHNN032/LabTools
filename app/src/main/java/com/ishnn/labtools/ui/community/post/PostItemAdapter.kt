package com.ishnn.labtools.ui.community.post

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.Global
import com.ishnn.labtools.R
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.util.animOptions
import java.io.Serializable
import java.lang.reflect.Array.newInstance
import java.text.SimpleDateFormat
import java.util.*

class PostItemAdapter(
    var items: MutableList<PostItem>,
    val fragment: PostFragment,
    val mContext: Context?
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
        val ivImage: ImageView
        init {
            with(item) {
                tvNotice = findViewById(R.id.post_item_tv_notice)
                tvTitle = findViewById(R.id.post_item_tv_title)
                tvNickname = findViewById(R.id.post_item_tv_nickname)
                tvTime = findViewById(R.id.post_item_tv_time)
                tvCommentCount = findViewById(R.id.post_item_tv_comment)
                ivImage = findViewById(R.id.post_item_iv_image)
            }

        }
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
                PostManager.getPosts(callback = callbackAll)
            }
            "즐겨찾기"->{
                PostManager.getFavorites(callback = callback)
            }
            "전체공지"->{
                PostManager.getNotices(callback = callbackAll)
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
        if(item.notice){
            (holder as ViewHolder).tvNotice.visibility = View.VISIBLE
        }else{
            (holder as ViewHolder).tvNotice.visibility = View.GONE
        }
        (holder as ViewHolder).tvTitle.text = item.title

        if(item.user != null){
            val callback: (String?) -> Unit = { name ->
                (holder as ViewHolder).tvNickname.text = name
            }
            PostManager.getUserName(item.user, callback = callback)
        }else{
            (holder as ViewHolder).tvNickname.text = "-"
        }

        if(item.time != null){
            val date = SimpleDateFormat("yyyy-MM-dd")
            val time = SimpleDateFormat("HH:mm")
            if(date.format(item.time) == date.format(Date())){
                (holder as ViewHolder).tvTime.text = time.format(item.time)
            }else{
                (holder as ViewHolder).tvTime.text = date.format(item.time)
            }
        }else{
            (holder as ViewHolder).tvTime.text = ""
        }

        (holder as ViewHolder).tvCommentCount.text = item.commentCount.toString()

        if (item.hasImage && mContext != null){
            val imageView = (holder as ViewHolder).ivImage
            imageView.visibility = View.VISIBLE
            val ref = Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${item.postId}/${Global.CROPPED_IMAGE}")
            GlideApp.with(mContext)
                .load(ref)
                .into(imageView)
        }

            holder.itemView.setOnClickListener {
            if(position != -1){
                Log.e("Click", "${items[position].postId}")
                val bundle = Bundle()
                bundle.putSerializable("post", items[position] as Serializable)

                findNavController(fragment).navigate(
                    R.id.action_nav_commu_to_postcontent,
                    bundle,
                    animOptions
                )
            }
        }
    }
}