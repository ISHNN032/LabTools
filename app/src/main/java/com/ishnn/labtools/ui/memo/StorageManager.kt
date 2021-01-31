package com.ishnn.labtools.ui.memo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.ishnn.labtools.model.MemoItem
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.*


object StorageManager {
    fun addMemo(
        content: String,
        Images: MutableMap<String, Uri>?,
        context: Context?){

        val time = Date(System.currentTimeMillis()).toString()
        File(time).mkdir()

        val fileName = "$time/$time.txt"
        var outputStream: FileOutputStream? = null

        if(! Images.isNullOrEmpty()){
            for(image in Images.toList()){
                //Todo save Images

            }
        }

        try {
            outputStream = context?.openFileOutput(fileName, MODE_PRIVATE)
            outputStream?.write(content.toByteArray())
            outputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun deleteMemo(time: Date){
        File(time.toString()).delete()
    }
//    fun getMemos(context: Context):List<MemoItem>{
//        context.dataDir
//    }
}