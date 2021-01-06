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


class PostContentFragment : Fragment(), IOnBackPressed {
//    private val adapter by lazy {
//        PostCommentAdapter(
//            (parentFragment as CommunityFragment).getPosts(),
//            ArrayList(),
//            this
//        )
//    }
//    private lateinit var mRecyclerView: RecyclerView
    private val commentAdapter by lazy { CommentItemAdapter(ArrayList()) }
    private lateinit var mPost: PostItem
    private lateinit var mPostContent: PostContent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_postcontent, container, false)
//        mRecyclerView = root.findViewById(R.id.recyclerView)

        mPost = arguments?.getSerializable("post") as PostItem
        if(mPost.user == null){
            dialogNoPost()
            return null
        }

        val callbackContent: (content: PostContent?) -> Unit = { content ->
            if(content == null){
                dialogNoPost()
            }else{
                parseContent(content!!.content!!, post_content_layout_content)
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
        if(mPost.notice!!){
            post_content_tv_notice.visibility = View.VISIBLE
        }else{
            post_content_tv_notice.visibility = View.GONE
        }
        post_content_tv_title.text = mPost.title
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        post_content_tv_time.text = time.format(mPost.time!!)
        post_content_tv_comment.text = mPost.commentCount.toString()
        post_content_tv_favorite.text = mPost.favoriteCount.toString()

        initRecyclerView()
        commentAdapter.refreshData()

        PostManager.addPostComment(mPost.postId, mPost.postId, "댓글입니다.")
        PostManager.addPostComment(mPost.postId, mPost.postId, "댓글입니다.")
        PostManager.addPostComment(mPost.postId, mPost.postId, "댓글입니다.")
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
    }

    private fun initRecyclerView() {
        post_content_lv_comment.adapter = commentAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        post_content_lv_comment.layoutManager = linearLayoutManager
    }


    fun parseContent(content: String, layout: LinearLayout){
        val split = content.split("[", "]")
        for(s in split){
            if(s.startsWith("#IMAGE:")){
                val image = ImageView(context)
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                image.layoutParams = lp
                val ref = Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${mPost.postId}/Sample.png")
                GlideApp.with(requireContext())
                    .load(ref)
                    .into(image)
                layout.addView(image)
            }else{
                val text = TextView(context)
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text.layoutParams = lp
                text.text = s
                layout.addView(text)
            }
        }
    }

    fun addData() {
//        adapter.addData()
    }

    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {0.
//            delay(2000)
//            withContext(Dispatchers.Main) {
//                adapter.removeLoader()
//                addData()
//                isLoading = false
//            }
//        }
    }

    private fun dialogNoPost(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("게시물을 찾을 수 없습니다.")
        builder.setMessage("삭제되었거나, 없는 게시물입니다.")
        NavHostFragment.findNavController(this).navigateUp()
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if(post_content_btn_menu != null){
            post_content_btn_menu.setOnClickListener { view ->
                val popupMenu = PopupMenu(context, view)
                inflater.inflate(R.menu.post_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when(it!!.itemId){
                        R.id.menu_post_modify -> {

                        }
                        R.id.menu_post_delete -> {
                            PostManager.deletePost(mPost.postId!!, mPostContent.hasImage!!)
                            //parentFragmentManager.popBackStack()
                            NavHostFragment.findNavController(this).navigateUp()
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}