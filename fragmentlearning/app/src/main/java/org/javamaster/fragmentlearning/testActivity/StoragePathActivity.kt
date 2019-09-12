package org.javamaster.fragmentlearning.testActivity

import android.content.Context
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_storge_path.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.util.*

class StoragePathActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_storge_path
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var stringBuilder = StringBuilder()
        stringBuilder.append("方法Environment.getExternalStorageState,状态:" + Environment.getExternalStorageState())
        stringBuilder.append("\r\n")
        // /data
        stringBuilder.append("方法Environment.getDataDirectory,具体路径:" + Environment.getDataDirectory())
        stringBuilder.append("\r\n")
        // /cache
        stringBuilder.append("方法Environment.getDownloadCacheDirectory,具体路径:" + Environment.getDownloadCacheDirectory())
        stringBuilder.append("\r\n")
        // /storage/sdcard
        stringBuilder.append("方法Environment.getExternalStorageDirectory,具体路径:" + Environment.getExternalStorageDirectory())
        stringBuilder.append("\r\n")
        // /system
        stringBuilder.append("方法Environment.getRootDirectory,具体路径:" + Environment.getRootDirectory())
        stringBuilder.append("\r\n")
        // /storage/sdcard/Pictures
        stringBuilder.append(
            "方法Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),具体路径:"
                    + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        )
        stringBuilder.append("\r\n")
        stringBuilder.append("\r\n")
        stringBuilder.append("\r\n")
        // /data/data/org.javamaster.fragmentlearning/files
        stringBuilder.append("方法Context.openFileInput/openFileOutput,具体路径:/data/data/${App.context.packageName}/files")
        stringBuilder.append("\r\n")
        // /data/data/org.javamaster.fragmentlearning/cache
        stringBuilder.append("方法Context.getCacheDir,具体路径:" + App.context.cacheDir)
        stringBuilder.append("\r\n")
        // /data/data/org.javamaster.fragmentlearning/code_cache
        stringBuilder.append("方法Context.getCodeCacheDir,具体路径:" + App.context.codeCacheDir)
        stringBuilder.append("\r\n")
        // /data/app/org.javamaster.fragmentlearning-1/base.apk
        stringBuilder.append("方法Context.getPackageCodePath,具体路径:" + App.context.packageCodePath)
        stringBuilder.append("\r\n")
        // /data/app/org.javamaster.fragmentlearning-1/base.apk
        stringBuilder.append("方法Context.getPackageResourcePath,具体路径:" + App.context.packageResourcePath)
        stringBuilder.append("\r\n")
        // /data/data/org.javamaster.fragmentlearning/app_myDir
        stringBuilder.append(
            "方法Context.getDir(\"myDir\", Context.MODE_PRIVATE),具体路径:" + App.context.getDir(
                "myDir",
                Context.MODE_PRIVATE
            )
        )
        stringBuilder.append("\r\n")
        // /data/data/org.javamaster.fragmentlearning/files
        stringBuilder.append("方法Context.getFilesDir,具体路径:" + App.context.filesDir)
        stringBuilder.append("\r\n")
        stringBuilder.append("\r\n")
        stringBuilder.append("\r\n")
        // /storage/sdcard/Android/data/org.javamaster.fragmentlearning/cache
        stringBuilder.append("方法Context.getExternalCacheDir,具体路径:" + App.context.externalCacheDir)
        stringBuilder.append("\r\n")
        // [/storage/sdcard/Android/data/org.javamaster.fragmentlearning/cache]
        stringBuilder.append("方法Context.getExternalCacheDirs,具体路径:" + Arrays.toString(App.context.externalCacheDirs))
        stringBuilder.append("\r\n")
        // /storage/sdcard/Android/media/org.javamaster.fragmentlearning
        stringBuilder.append("方法Context.getExternalMediaDirs,具体路径:" + Arrays.toString(App.context.externalMediaDirs))
        stringBuilder.append("\r\n")
        // /storage/sdcard/Android/obb/org.javamaster.fragmentlearning
        stringBuilder.append("方法Context.getObbDir,具体路径:" + App.context.obbDir)
        stringBuilder.append("\r\n")
        // /storage/sdcard/Android/data/org.javamaster.fragmentlearning/files
        stringBuilder.append("方法Context.getExternalFilesDir(null),具体路径:" + App.context.getExternalFilesDir(null))
        stringBuilder.append("\r\n")
        // /storage/sdcard/Android/data/org.javamaster.fragmentlearning/files/myDir
        stringBuilder.append("方法Context.getExternalFilesDir(\"myDir\"),具体路径:" + App.context.getExternalFilesDir("myDir"))
        stringBuilder.append("\r\n")
        textView6.text = stringBuilder.toString()
    }
}
