package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_take_photo.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import org.javamaster.fragmentlearning.utils.ImageUtils
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
                    displayImage(ImageUtils.resolvePhoto(data))
                } else {
                    Toast.makeText(this, "你取消了选择", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
