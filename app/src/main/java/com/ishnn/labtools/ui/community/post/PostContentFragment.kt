package com.ishnn.labtools.ui.community.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.ishnn.labtools.Global
import com.ishnn.labtools.R
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
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
    private lateinit var mPost: PostItem

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
            return null
            //Todo 없는 게시물입니다.
        }

        val callbackContent: (content: PostContent?) -> Unit = { content ->
            parseContent(content!!.content!!, post_content_layout_content)
        }
        val callbackName: (String?) -> Unit = { name ->
            post_content_tv_nickname.text = name
        }
        PostManager.getPostContent(mPost.postId!!, callback = callbackContent)
        PostManager.getUserName(mPost.user!!, callback = callbackName)
        return root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
    }

    private fun initRecyclerView() {
//        mRecyclerView.adapter = adapter
//        val linearLayoutManager = LinearLayoutManager(requireContext())
//        mRecyclerView.layoutManager = linearLayoutManager
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
                val ref = Global.storage.reference.child("post/content/${mPost.postId}/Sample.png")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}