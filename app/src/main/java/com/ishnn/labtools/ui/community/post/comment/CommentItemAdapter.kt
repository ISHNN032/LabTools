package com.ishnn.labtools.ui.community.post.comment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.R
import com.ishnn.labtools.model.CommentItem
import com.ishnn.labtools.ui.community.post.GlideApp
import com.ishnn.labtools.ui.community.post.PostManager
import java.text.SimpleDateFormat

class CommentItemAdapter(
    var items: MutableList<CommentItem>,
    var postId: String,
    val mContext: Context?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvNickname: TextView
        val tvTime: TextView
        val tvContent: TextView
        val ivImage: ImageView
        val nasted: View
        val menuButton: ImageButton

        init {
            with(item) {
                tvNickname = findViewById(R.id.comment_item_tv_nickname)
                tvTime = findViewById(R.id.comment_item_tv_time)
                tvContent = findViewById(R.id.comment_item_tv_content)
                ivImage = findViewById(R.id.comment_item_iv_image)
                nasted = findViewById(R.id.comment_item_nested)
                menuButton = findViewById(R.id.comment_item_btn_menu)
            }
        }
    }

    interface ClickListener {
        fun onClickListener(pos: Int, aView: View)
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem: CommentItem, pos: Int) {
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun refreshData() {
        items.clear()
        addData()
    }

    fun addData() {
        val callbackAll: (List<CommentItem>) -> Unit = { data ->
            items.addAll(data)
            notifyDataSetChanged()
        }
        PostManager.getPostComments(postId, callback = callbackAll)
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (item.hasImage && mContext != null) {
            val imageView = (holder as ViewHolder).ivImage
            imageView.visibility = View.VISIBLE
            val ref =
                Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}/${Global.CROPPED_IMAGE}")
            GlideApp.with(mContext)
                .load(ref)
                .into(imageView)
        }

        if (item.nested) {
            (holder as ViewHolder).nasted.visibility = View.VISIBLE
        }

        val callback: (String?) -> Unit = { name ->
            (holder as ViewHolder).tvNickname.text = name
        }
        PostManager.getUserName(item.user!!, callback = callback)

        (holder as ViewHolder).tvContent.text = item.content

        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        (holder as ViewHolder).tvTime.text = time.format(item.time!!)

        if (item.user == GlobalLogin.getUserData()?.id) {
            (holder as ViewHolder).menuButton.visibility = View.VISIBLE
            (holder as ViewHolder).menuButton.setOnClickListener { view ->
                val popupMenu = PopupMenu(mContext, view)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it!!.itemId) {
                        R.id.menu_post_modify -> {

                        }
                        R.id.menu_post_delete -> {
                            PostManager.deleteComment(postId, item.commentId!!, item.hasImage)
                            refreshData()
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }
}