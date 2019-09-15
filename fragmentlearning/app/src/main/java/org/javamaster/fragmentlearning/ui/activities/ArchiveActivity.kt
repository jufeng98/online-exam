package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_archive.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.CountryAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.utils.ImageUtils
import org.javamaster.fragmentlearning.utils.NetUtils
import org.javamaster.fragmentlearning.utils.StreamUtils
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject
import kotlin.concurrent.thread

class ArchiveActivity : BaseAppActivity() {

    lateinit var userInfo: User
    @Inject
    lateinit var loginService: LoginService

    override fun initContentView(): Int? {
        return R.layout.activity_archive
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        userInfo = App.objectMapper.readValue(
            App.getLoginSharedPreferences().getString(LoginService.LOGIN_USER_INFO, ""),
            User::class.java
        )
        val bitmap = ImageUtils.getUserPhoto()
        if (bitmap != null) {
            user_photo.setImageBitmap(bitmap)
        }
        username.text = userInfo.username
        email.text = userInfo.email
        if (userInfo.gender == "M") {
            gender.isChecked = true
            gender.text = getString(R.string.male)
        } else {
            gender.isChecked = false
            gender.text = getString(R.string.female)
        }
        gender.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.text = getString(R.string.male)
            } else {
                buttonView.text = getString(R.string.female)
            }
        }
        mobile.setText(userInfo.mobile)
        remark.setText(userInfo.remark)

        val list: List<Pair<Int, Int>> = listOf(Pair(R.drawable.cn, R.string.china), Pair(R.drawable.us, R.string.usa))
        val adapter = CountryAdapter(R.layout.archive_country, list)
        spinner.adapter = adapter
    }

    @OnClick(R.id.user_photo)
    fun pickPhoto() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    @OnClick(R.id.confirm)
    fun save() {
        userInfo.username = username.text as String
        if (user_photo_path.text != "") {
            userInfo.picUrl = user_photo_path.text as String
        }
        userInfo.gender = if (gender.isChecked) gender.textOn as String else gender.textOff as String
        userInfo.mobile = mobile.text.toString()
        userInfo.remark = remark.text.toString()
        thread {
            val resultVo = loginService.editUsers(userInfo)
            runOnUiThread {
                if (resultVo.success) {
                    val picUrl = userInfo.picUrl
                    if (picUrl != "") {
                        val suffix = picUrl.substring(picUrl.lastIndexOf("."))
                        ImageUtils.saveUserPhoto(suffix, user_photo_path.tag as ByteArray)
                    }
                    Toast.makeText(this@ArchiveActivity, getString(R.string.change_success), Toast.LENGTH_SHORT).show()
                } else {
                    if (resultVo.errorCode != AppConsts.LOGIN_ERROR_CODE) {
                        Toast.makeText(this@ArchiveActivity, resultVo.errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val imagePath = ImageUtils.resolvePhoto(data)
            val bitmap = BitmapFactory.decodeFile(imagePath)
            user_photo.setImageBitmap(bitmap)
            NetUtils.uploadImage(AppConsts.UPLOAD_FILE, File(imagePath), object : OperationListener<String> {
                override fun success(t: String) {
                    user_photo_path.text = t
                    FileInputStream(imagePath).use {
                        user_photo_path.tag = StreamUtils.copyToByteArray(it)
                    }
                }
            })
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, ArchiveActivity::class.java)
            context.startActivity(intent)
        }
    }

}
