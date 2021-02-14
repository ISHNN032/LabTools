package com.ishnn.labtools.ui.memo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.ishnn.labtools.model.MemoItem
import com.nhn.android.naverlogin.OAuthLoginDefine.LOG_TAG
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


object StorageManager {
    const val GET_GALLERY_IMAGE_MEMO = 300

    fun addMemo(
        title: String,
        content: String,
        Images: MutableMap<String, Uri>?,
        context: Context?
    ) {
        val time = Date(System.currentTimeMillis()).toString()
        val fileName = "$title.txt"

//        if(! Images.isNullOrEmpty()){
//            for(image in Images.toList()){
//                //Todo save Images
//            }
//        }
        Log.e("addMemo", fileName)
        writeFile(context, fileName, content)
    }

    fun writeFile(context: Context?, fileName: String, content: String) {
        val file = File(
            context?.getExternalFilesDir("memos"),
            fileName
        )
        try {
            val fw = FileWriter(file, false)
            fw.write(content)
            fw.close()
        } catch (e: IOException) {
            Log.e("writeFile", e.toString())
        }
    }

    fun deleteMemo(time: Date) {
        File(time.toString()).delete()
    }

    fun getMemos(context: Context?, callback: (List<MemoItem>) -> Unit) {
        Log.e("getMemos", "getting")
        val list = ArrayList<MemoItem>()
        for (i in context?.getExternalFilesDir("memos")?.listFiles()!!) {
            Log.e("files", i.absolutePath)
            list.add(MemoItem())
        }
        callback(list)
    }
//    fun getMemos(context: Context):List<MemoItem>{
//        context.dataDir
//    }
}