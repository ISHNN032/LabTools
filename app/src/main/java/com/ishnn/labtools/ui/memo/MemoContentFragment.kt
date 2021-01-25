package com.ishnn.labtools.ui.memo

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
import com.ishnn.labtools.model.MemoItem
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
import com.ishnn.labtools.ui.community.post.comment.CommentItemAdapter
import com.ishnn.labtools.ui.tools.HistoryActionListDialogFragment
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_memocontent.*
import kotlinx.android.synthetic.main.fragment_postcontent.*
import kotlinx.android.synthetic.main.item_post_comment.*
import java.text.SimpleDateFormat


class MemoContentFragment : Fragment(), IOnBackPressed {
    private lateinit var mMemo: MemoItem

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
        val root = inflater.inflate(R.layout.fragment_memocontent, container, false)
        mMemo = arguments?.getSerializable("memo") as MemoItem
        return root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        memo_content_tv_title.text = mMemo.title
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm")
        memo_content_tv_time.text = time.format(mMemo.time!!)
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
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
//                val ref =
//                    Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${mPost.postId}/$name")
//                GlideApp.with(requireContext())
//                    .load(ref)
//                    .into(image)
//                layout.addView(image)

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

    private fun dialogDeleteMemo() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("메모를 삭제합니다.")
        builder.setPositiveButton("확인", DialogInterface.OnClickListener { _, _ ->
            StorageManager.deleteMemo(mMemo.time!!, mMemo.hasImage)
            NavHostFragment.findNavController(this).navigateUp()
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
        })
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        if (memo_content_btn_menu != null) {
            memo_content_btn_menu.setOnClickListener { view ->
                val popupMenu = PopupMenu(context, view)
                inflater.inflate(R.menu.post_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it!!.itemId) {
                        R.id.menu_post_modify -> {

                        }
                        R.id.menu_post_delete -> {
                            dialogDeleteMemo()
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}