package org.javamaster.fragmentlearning.utils

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import org.javamaster.fragmentlearning.common.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @author yudong
 * @date 2019/9/6
 */
object ImageUtils {
    private const val USER_PHOTO_KEY = "userPhotoFileName"

    fun saveUserPhoto(suffix: String, t: ByteArray) {
        val path = App.context.getDir("img", Context.MODE_PRIVATE)
        val preferences = App.getLoginSharedPreferences()
        val editor = preferences.edit()
        editor.putString(USER_PHOTO_KEY, "user_photo$suffix")
        editor.apply()
        val file = File(path, "user_photo$suffix")
        FileOutputStream(file).use {
            StreamUtils.copy(t, it)
        }
    }

    fun getUserPhoto(): Bitmap? {
        val path = App.context.getDir("img", Context.MODE_PRIVATE)
        if (!path.exists()) {
            return null
        }
        val preferences = App.getLoginSharedPreferences()
        val name = preferences.getString(USER_PHOTO_KEY, "")
        if (name == "") {
            return null
        }
        val file = File(path, preferences.getString(USER_PHOTO_KEY, ""))
        if (!file.exists()) {
            return null
        }
        FileInputStream(file).use { input ->
            ByteArrayOutputStream().use {
                StreamUtils.copy(input, it)
                val bytes = it.toByteArray()
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }
    }

    fun resolvePhoto(data: Intent?): String? {
        return if (Build.VERSION.SDK_INT >= 19) {
            // 4.4及以上系统使用这个方法处理图片
            data?.let { handleImageOnKitKat(it) }
        } else {
            // 4.4以下系统使用这个方法处理图片
            data?.let { handleImageBeforeKitKat(it) }
        }
    }

    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent): String? {
        var imagePath: String? = null
        val uri = data.data
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri!!)
        if (DocumentsContract.isDocumentUri(App.context, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.path
        }
        return imagePath
    }

    private fun handleImageBeforeKitKat(data: Intent): String? {
        val uri = data.data
        return getImagePath(uri, null)
    }

    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = App.context.contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

}