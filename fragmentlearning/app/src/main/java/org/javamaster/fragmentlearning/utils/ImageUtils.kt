package org.javamaster.fragmentlearning.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
}