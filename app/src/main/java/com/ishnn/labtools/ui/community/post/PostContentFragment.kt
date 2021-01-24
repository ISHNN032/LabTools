package com.ishnn.labtools.ui.community.post

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishnn.labtools.Global
import com.ishnn.labtools.Global.IMAGE_TAG
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.R
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.ui.community.post.comment.CommentItemAdapter
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_postcontent.*
import kotlinx.android.synthetic.main.item_post_comment.*
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
    private lateinit var mPostContent: PostContent
    private val commentAdapter by lazy {
        CommentItemAdapter(
            ArrayList(),
            mPost.postId!!,
            this,
            context
        )
    }

    //for comment
    private var mCommentImage: Uri? = null
    private var mCommentToNest: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //refresh values
        mCommentImage = null
        mCommentToNest = null

        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_postcontent, container, false)
//        mRecyclerView = root.findViewById(R.id.recyclerView)

        mPost = arguments?.getSerializable("post") as PostItem
        if (mPost.user == null) {
            dialogNoPost()
            return null
        }

        val callbackContent: (content: PostContent?) -> Unit = { content ->
            if (content == null) {
                dialogNoPost()
            } else {
                parseContent(content.content!!, post_content_layout_content)
                mPostContent = content
            }
        }
        val callbackName: (String?) -> Unit = { name ->
            post_content_tv_nickname.text = name
        }
        PostManager.getPostContent(mPost.postId!!, callback = callbackContent)
        PostManager.getUserName(mPost.user!!, callback = callbackName)

        if (mPost.user == GlobalLogin.getUserData()?.id) {
            root.findViewById<ImageButton>(R.id.post_content_btn_menu).visibility = View.VISIBLE
        }
        return root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mPost.notice) {
            post_content_tv_notice.visibility = View.VISIBLE
        } else {
            post_content_tv_notice.visibility = View.GONE
        }
        post_content_tv_title.text = mPost.title
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        post_content_tv_time.text = time.format(mPost.time!!)
        post_content_tv_comment.text = mPost.commentCount.toString()


        val postComment = post_content_include_post_comment
        if (GlobalLogin.getUserLoggedIn()) {
            postComment.visibility = View.VISIBLE
        }
        val buttonCommentImage =
            postComment.findViewById<ImageButton>(R.id.item_post_comment_bt_image)
        buttonCommentImage.setOnClickListener {
            getImageFromStorage()
        }
        val buttonCommentSave = postComment.findViewById<Button>(R.id.item_post_comment_bt_save)
        buttonCommentSave.setOnClickListener {
            val hasImage = mCommentImage != null
            val isNested = !mCommentToNest.isNullOrEmpty()
            PostManager.addPostComment(
                mPost.postId!!,
                mCommentToNest,
                postComment.findViewById<EditText>(R.id.item_post_comment_text).text.toString(),
                hasImage = hasImage,
                isNested = isNested,
                image = mCommentImage
            )
            PostManager.updateCommentCount(mPost.postId!!)
            refreshFragment()
        }

        initRecyclerView()
        commentAdapter.refreshData()

//        PostManager.addPostComment(mPost.postId!!, "0", "댓글입니다. 0",
//            hasImage = false,
//            isNested = false,
//            image = null
//        )
//        PostManager.addPostComment(mPost.postId!!, "0n0", "대댓글입니다. 0a",
//            hasImage = false,
//            isNested = true,
//            image = null
//        )
//        PostManager.addPostComment(mPost.postId!!, "0n1", "대댓글입니다. 0b",
//            hasImage = false,
//            isNested = true,
//            image = null
//        )
//        PostManager.addPostComment(mPost.postId!!, "1", "댓글입니다. 1",
//            hasImage = false,
//            isNested = false,
//            image = null
//        )
//        PostManager.addPostComment(mPost.postId!!, "2", "댓글입니다. 2",
//            hasImage = false,
//            isNested = false,
//            image = null
//        )
//        PostManager.addPostComment(mPost.postId!!, "2n0", "대댓글입니다. 2a",
//            hasImage = false,
//            isNested = true,
//            image = null
//        )
    }

    fun refreshFragment() {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.setReorderingAllowed(false)
        ft.detach(this).attach(this).commitAllowingStateLoss()
    }

    @SuppressLint("SetTextI18n")
    fun setCommentNested(nested: String?) {
        mCommentToNest = nested
        post_content_layout_scroll.scrollTo(
            post_content_include_post_comment.scrollX,
            post_content_include_post_comment.scrollY
        )
        item_post_comment_text_nested.visibility = View.VISIBLE
        item_post_comment_text_nested.text = "<<$nested"
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


    fun parseContent(content: String, layout: LinearLayout) {
        val split = content.split("[", "]")
        for (s in split) {
            if (s.startsWith(IMAGE_TAG)) {
                val name = s.replaceFirst(IMAGE_TAG, "")
                val image = ImageView(context)
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                image.adjustViewBounds = true
                image.layoutParams = lp
                val ref =
                    Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${mPost.postId}/$name")
                GlideApp.with(requireContext())
                    .load(ref)
                    .into(image)
                layout.addView(image)
            } else {
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

    private fun dialogNoPost() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("게시물을 찾을 수 없습니다.")
        builder.setMessage("삭제되었거나, 없는 게시물입니다.")
        NavHostFragment.findNavController(this).navigateUp()
        builder.show()
    }

    private fun dialogDeletePost() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("작성글을 삭제합니다.")
        builder.setPositiveButton("확인", DialogInterface.OnClickListener { _, _ ->
            PostManager.deletePost(mPost.postId!!, mPostContent.hasImage!!)
            NavHostFragment.findNavController(this).navigateUp()
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
        })
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (post_content_btn_menu != null) {
            post_content_btn_menu.setOnClickListener { view ->
                val popupMenu = PopupMenu(context, view)
                inflater.inflate(R.menu.post_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it!!.itemId) {
                        R.id.menu_post_modify -> {

                        }
                        R.id.menu_post_delete -> {
                            dialogDeletePost()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("RE", "$requestCode, $resultCode, ${data?.data}")
        if (requestCode == PostManager.GET_GALLERY_IMAGE_COMMENT && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            item_post_comment_image.setImageURI(selectedImageUri)
            item_post_comment_image.visibility = View.VISIBLE
            mCommentImage = selectedImageUri
        }
    }

    fun getFileName(uri: Uri?): String? {
        var result: String? = null
        if (uri != null) {
            if (uri.scheme == "content") {
                val cursor: Cursor? = activity?.contentResolver?.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result =
                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } finally {
                    cursor?.close()
                }
            }
        }
        if (result == null) {
            if (uri != null) {
                result = uri.path
            }
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private fun getImageFromStorage() {
        var temp = ""
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE.toString() + " "
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE.toString() + " "
        }
        if (TextUtils.isEmpty(temp) == false) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                temp.trim { it <= ' ' }.split(" ".toRegex()).toTypedArray(),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            );
            startActivityForResult(intent, PostManager.GET_GALLERY_IMAGE_COMMENT);
        }
    }
}