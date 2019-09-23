package org.javamaster.fragmentlearning.ui.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.ArchiveBaseState
import org.javamaster.fragmentlearning.data.ArchivePhotoState
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.utils.ImageUtils
import org.javamaster.fragmentlearning.utils.NetUtils
import org.javamaster.fragmentlearning.utils.StreamUtils
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * @author yudong
 * @date 2019/9/22
 */
class ArchiveViewModel @Inject constructor(private val loginService: LoginService) : ViewModel() {

    private val _archivePhotoState = MutableLiveData<ArchivePhotoState>()
    val archivePhotoState: LiveData<ArchivePhotoState> = _archivePhotoState

    private val _archiveBaseState = MutableLiveData<ArchiveBaseState>()
    val archiveBaseState: LiveData<ArchiveBaseState> = _archiveBaseState

    private val _archiveResult = MutableLiveData<ResultVo<Int>>()
    val archiveResult: LiveData<ResultVo<Int>> = _archiveResult

    fun editUser(userInfo: User) {
        thread {
            val resultVo = loginService.editUsers(userInfo)
            if (!resultVo.success) {
                _archiveResult.postValue(resultVo)
                return@thread
            }
            val picUrl = userInfo.picUrl
            if (_archivePhotoState.value?.photoByte != null) {
                val suffix = picUrl.substring(picUrl.lastIndexOf("."))
                ImageUtils.saveUserPhoto(suffix, _archivePhotoState.value!!.photoByte)
            }
            _archiveResult.postValue(resultVo)
        }
    }

    fun photoChange(userPhotoData: Intent?) {
        val imagePath = ImageUtils.resolvePhoto(userPhotoData)!!
        NetUtils.uploadImage(AppConsts.UPLOAD_FILE, File(imagePath), object : OperationListener<String> {
            override fun success(t: String) {
                FileInputStream(imagePath).use {
                    _archivePhotoState.postValue(ArchivePhotoState(imagePath, t, StreamUtils.copyToByteArray(it)))
                }
            }
        })
    }

    fun archiveBaseChanged(mobile: String, remark: String) {
        val archiveBaseState = ArchiveBaseState()
        if (mobile.length != 11) {
            archiveBaseState.mobileError = R.string.wrong_mobile
        }
        if (remark.length > 50) {
            archiveBaseState.remarkError = R.string.remark_limit
        }
        _archiveBaseState.value = archiveBaseState
    }

}
