package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_archive.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.CountryAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.ui.viewModel.ArchiveViewModel
import org.javamaster.fragmentlearning.utils.ImageUtils
import javax.inject.Inject

class ArchiveActivity : BaseAppActivity() {

    private lateinit var userInfo: User
    @Inject
    lateinit var loginService: LoginService
    @Inject
    lateinit var archiveViewModel: ArchiveViewModel

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
        var bitmap = ImageUtils.getUserPhoto()
        if (bitmap != null) {
            user_photo.setImageBitmap(bitmap)
        }
        email.text = userInfo.email
        if (userInfo.gender == "M") {
            gender.isChecked = true
            gender.text = getString(R.string.male)
        } else {
            gender.isChecked = false
            gender.text = getString(R.string.female)
        }
        username.text = userInfo.username
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
        mobile.afterTextChanged {
            archiveViewModel.archiveBaseChanged(mobile.text.toString(), remark.text.toString())
        }
        remark.afterTextChanged {
            archiveViewModel.archiveBaseChanged(mobile.text.toString(), remark.text.toString())
        }

        archiveViewModel.archivePhotoState.observe(this, Observer {
            bitmap = BitmapFactory.decodeFile(it.photoPath)
            user_photo.setImageBitmap(bitmap)
        })
        archiveViewModel.archiveBaseState.observe(this, Observer {
            if (it.mobileError != null) {
                mobile.error = getString(it.mobileError!!)
            }
            if (it.remarkError != null) {
                remark.error = getString(it.remarkError!!)
            }
        })
        archiveViewModel.archiveResult.observe(this, Observer {
            if (!it.success) {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                return@Observer
            }
            Toast.makeText(this, getString(R.string.change_success), Toast.LENGTH_SHORT).show()
            finish()
        })
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
        if (archiveViewModel.archivePhotoState.value?.serverPath != "") {
            userInfo.picUrl = archiveViewModel.archivePhotoState.value!!.serverPath
        }
        userInfo.gender = if (gender.isChecked) gender.textOn as String else gender.textOff as String
        userInfo.mobile = mobile.text.toString()
        userInfo.remark = remark.text.toString()
        archiveViewModel.editUser(userInfo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            archiveViewModel.photoChange(data)
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, ArchiveActivity::class.java)
            context.startActivity(intent)
        }
    }

}
