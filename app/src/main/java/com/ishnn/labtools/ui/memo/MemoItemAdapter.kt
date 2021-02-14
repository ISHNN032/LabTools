package com.ishnn.labtools.ui.memo

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
import com.ishnn.labtools.model.MemoItem
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.ui.community.post.GlideApp
import com.ishnn.labtools.util.animOptions
import java.io.Serializable
import java.lang.reflect.Array.newInstance
import java.text.SimpleDateFormat
import java.util.*

class MemoItemAdapter(
    var items: MutableList<MemoItem>,
    val fragment: MemoFragment,
    val mContext: Context?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memo, parent, false)
        return ViewHolder(view)
    }
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvTitle: TextView
        val tvTime: TextView
        val ivImage: ImageView
        init {
            with(item) {
                tvTitle = findViewById(R.id.memo_item_tv_title)
                tvTime = findViewById(R.id.memo_item_tv_time)
                ivImage = findViewById(R.id.memo_item_iv_image)
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as ViewHolder).tvTitle.text = item.content
        if (item.hasImage && mContext != null){
            val imageView = (holder as ViewHolder).ivImage
            imageView.visibility = View.VISIBLE
//            val ref = Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${item.postId}/${Global.CROPPED_IMAGE}")
//            GlideApp.with(mContext)
//                .load(ref)
//                .into(imageView)
        }
            holder.itemView.setOnClickListener {
            if(position != -1){
                Log.e("Click", "${items[position].time}")
                val bundle = Bundle()
                bundle.putSerializable("memo", items[position] as Serializable)

                findNavController(fragment).navigate(
                    R.id.action_memo_to_memocontent,
                    bundle,
                    animOptions
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem: MemoItem, pos: Int){
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun refreshData(){
        items.clear()
        addData()
    }

    fun addData(){
        val callbackAll: (List<MemoItem>) -> Unit = { data ->
            items.addAll(data)
            notifyDataSetChanged()
        }
        StorageManager.getMemos(mContext, callback = callbackAll)
        //PostManager.getPosts(callback = callbackAll)
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }
}