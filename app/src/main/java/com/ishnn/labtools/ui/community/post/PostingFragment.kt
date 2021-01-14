package com.ishnn.labtools.ui.community.post

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.R
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.ui.community.post.comment.CommentItemAdapter
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_postcontent.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat


class PostingFragment : Fragment(), IOnBackPressed {
    private lateinit var mPost: PostItem
    private lateinit var mPostContent: PostContent
    private val commentAdapter by lazy { CommentItemAdapter(ArrayList(), mPost.postId!!, context)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_posting, container, false)

        mPost = arguments?.getSerializable("post") as PostItem
        if(mPost.user == null){
            dialogNoPost()
            return null
        }

        val callbackContent: (content: PostContent?) -> Unit = { content ->
            if(content == null){
                dialogNoPost()
            }else{
                mPostContent = content
            }
        }
        val callbackName: (String?) -> Unit = { name ->
            post_content_tv_nickname.text = name
        }
        PostManager.getPostContent(mPost.postId!!, callback = callbackContent)
        PostManager.getUserName(mPost.user!!, callback = callbackName)

        if(mPost.user == GlobalLogin.getUserData()?.id){
            root.findViewById<ImageButton>(R.id.post_content_btn_menu).visibility = View.VISIBLE
        }
        return root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(mPost.notice){
            post_content_tv_notice.visibility = View.VISIBLE
        }else{
            post_content_tv_notice.visibility = View.GONE
        }
        post_content_tv_title.text = mPost.title
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        post_content_tv_time.text = time.format(mPost.time!!)
        post_content_tv_comment.text = mPost.commentCount.toString()
        post_content_tv_favorite.text = mPost.favoriteCount.toString()
        commentAdapter.refreshData()

        PostManager.addPostComment(mPost.postId!!, "0", "댓글입니다. 0",
            hasImage = false,
            isNested = false
        )
        PostManager.addPostComment(mPost.postId!!, "0a", "대댓글입니다. 0a",
            hasImage = false,
            isNested = true
        )
        PostManager.addPostComment(mPost.postId!!, "0b", "대댓글입니다. 0b",
            hasImage = false,
            isNested = true
        )
        PostManager.addPostComment(mPost.postId!!, "1", "댓글입니다. 1",
            hasImage = false,
            isNested = false
        )
        PostManager.addPostComment(mPost.postId!!, "2", "댓글입니다. 2",
            hasImage = false,
            isNested = false
        )
        PostManager.addPostComment(mPost.postId!!, "2a", "대댓글입니다. 2a",
            hasImage = false,
            isNested = true
        )
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
    }

    private fun dialogNoPost(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("게시물을 찾을 수 없습니다.")
        builder.setMessage("삭제되었거나, 없는 게시물입니다.")
        NavHostFragment.findNavController(this).navigateUp()
        builder.show()
    }
}