package com.ishnn.labtools.ui.community.post

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_posting.*


class PostingFragment : Fragment(), IOnBackPressed, View.OnClickListener {
    private val mImages : MutableMap<String,Uri> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_posting, container, false)
        return root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        posting_button_exit.setOnClickListener(this)
        posting_button_cancel.setOnClickListener(this)
        posting_button_post.setOnClickListener(this)
        posting_button_save.setOnClickListener(this)
        posting_button_image.setOnClickListener(this)
    }

    override fun onBackPressed(): Boolean {
        dialogExit()
        return false
    }

    override fun onClick(view: View?) {
        when(view?.id){
            posting_button_exit.id ,
            posting_button_cancel.id -> {
                dialogExit()
            }

            posting_button_post.id,
            posting_button_save.id -> {
                var hasImage = false
                if(mImages.size >= 0){
                    hasImage = true
                }
                PostManager.addPost(posting_et_title.text.toString(), posting_et_content.text.toString(), hasImage, mImages, context)
            }

            posting_button_image.id -> {
                getImageFromStorage()
            }
        }
    }

    private fun dialogExit(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("작성을 취소합니다")
        builder.setMessage("작성하던 Post 내용은 저장되지 않습니다.")
        builder.setPositiveButton("확인", DialogInterface.OnClickListener { _, _ ->
            NavHostFragment.findNavController(this).navigateUp()
        })
        builder.setNegativeButton("돌아가기", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
        })
        builder.show()
    }

    fun getImageFromStorage() {
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
        if (!TextUtils.isEmpty(temp)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                temp.trim { it <= ' ' }.split(" ".toRegex()).toTypedArray(),
                1
            )
        }
        else{
            val intent = Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, PostManager.GET_GALLERY_IMAGE_POST);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("RE", "$requestCode, $resultCode, ${data?.data}")
        if (requestCode == PostManager.GET_GALLERY_IMAGE_POST && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            posting_et_content.text.insert(0, "\n[#IMAGE:${getFileName(selectedImageUri)}]\n")
            mImages[getFileName(data?.data!!)] = data.data!!
        }
    }

    fun getFileName(uri: Uri?): String{
        var result: String? = null
        if (uri != null) {
            if (uri.scheme == "content") {
                val cursor: Cursor? = activity?.contentResolver?.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
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
}