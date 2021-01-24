package com.ishnn.labtools.ui.community.post.comment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.ishnn.labtools.ui.community.post.PostContentFragment
import com.ishnn.labtools.ui.community.post.PostManager
import java.text.SimpleDateFormat

class CommentItemAdapter(
    var items: MutableList<CommentItem>,
    var postId: String,
    val fragment: PostContentFragment,
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
        val commentButton: TextView
        val editButton: TextView
        val deleteButton: TextView

        init {
            with(item) {
                tvNickname = findViewById(R.id.comment_item_tv_nickname)
                tvTime = findViewById(R.id.comment_item_tv_time)
                tvContent = findViewById(R.id.comment_item_tv_content)
                ivImage = findViewById(R.id.comment_item_iv_image)
                nasted = findViewById(R.id.comment_item_nested)
                commentButton = findViewById(R.id.comment_item_bt_comment)
                editButton = findViewById(R.id.comment_item_bt_edit)
                deleteButton = findViewById(R.id.comment_item_bt_delete)
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
                Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}/${item.commentId}.jpg")
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
        holder.tvTime.text = time.format(item.time!!)

        if (GlobalLogin.getUserData() != null){
            holder.commentButton.visibility = View.VISIBLE
            holder.commentButton.setOnClickListener {
                fragment.setCommentNested(item.commentId)
            }
        }

        if (item.user == GlobalLogin.getUserData()?.id) {
            //holder.editButton.visibility = View.VISIBLE
            holder.editButton.setOnClickListener {

            }
            holder.deleteButton.visibility = View.VISIBLE
            holder.deleteButton.setOnClickListener {
                dialogDeleteComment(item)
            }
        }
    }

    private fun dialogDeleteComment(item: CommentItem){
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setTitle("댓글을 삭제합니다.")
        builder.setPositiveButton("확인", DialogInterface.OnClickListener { _, _ ->
            PostManager.deleteComment(postId, item.commentId!!, item.hasImage)
            fragment.refreshFragment()
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
        })
        PostManager.updateCommentCount(postId)
        builder.show()
    }
}