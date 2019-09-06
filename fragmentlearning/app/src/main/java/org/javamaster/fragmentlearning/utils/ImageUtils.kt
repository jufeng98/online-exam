package org.javamaster.fragmentlearning.utils

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
        var path = App.context.getExternalFilesDir("").path + "/img/"
        var parent = File(path)
        if (!parent.exists()) {
            parent.mkdirs()
        }
        var preferences = App.getLoginSharedPreferences()
        var editor = preferences.edit()
        editor.putString(USER_PHOTO_KEY, "user_photo$suffix")
        editor.commit()
        var file = File(path, "user_photo$suffix")
        var outputStream = FileOutputStream(file)
        StreamUtils.copy(t, outputStream)
        outputStream.close()
    }

    fun getUserPhoto(): Bitmap? {
        var path = App.context.getExternalFilesDir("").path + "/img/"
        var preferences = App.getLoginSharedPreferences()
        var file = File(path, preferences.getString(USER_PHOTO_KEY, ""))
        if (!file.exists()) {
            return null
        }
        var inputStream = FileInputStream(file)
        var outputStream = ByteArrayOutputStream()
        StreamUtils.copy(inputStream, outputStream)
        var bytes = outputStream.toByteArray()
        inputStream.close()
        outputStream.close()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}