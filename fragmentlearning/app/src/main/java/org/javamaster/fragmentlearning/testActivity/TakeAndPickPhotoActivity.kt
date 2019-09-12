package org.javamaster.fragmentlearning.testActivity

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_take_photo.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.File


class TakeAndPickPhotoActivity : BaseAppActivity() {
    private lateinit var imageUri: Uri
    override fun initContentView(): Int? {
        return R.layout.activity_take_photo
    }

    @OnClick(R.id.take_photo)
    fun takePhoto() {
        var file = File(externalCacheDir, "output_image.jpg")
        if (file.exists()) {
            file.delete()
        }
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(this, "org.javamaster", file)
        } else {
            Uri.fromFile(file)
        }
        var intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 1)
        imageView8.setImageBitmap(null)
    }

    @OnClick(R.id.pick_photo)
    fun pickPhoto() {
        var intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    var map = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    imageView8.setImageBitmap(map)
                } else {
                    Toast.makeText(this, "你取消了拍摄", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        data?.let { handleImageOnKitKat(it) }
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        data?.let { handleImageBeforeKitKat(it) }
                    }
                } else {
                    Toast.makeText(this, "你取消了选择", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri!!)
        if (DocumentsContract.isDocumentUri(this, uri)) {
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
        displayImage(imagePath) // 根据图片路径显示图片
    }

    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        val imagePath = getImagePath(uri, null)
        displayImage(imagePath)
    }

    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imageView8.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, TakeAndPickPhotoActivity::class.java)
            context.startActivity(intent)
        }
    }
}
