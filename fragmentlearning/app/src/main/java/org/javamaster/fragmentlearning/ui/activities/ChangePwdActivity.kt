package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_pwd.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.service.LoginService
import javax.inject.Inject
import kotlin.concurrent.thread

class ChangePwdActivity : BaseAppActivity() {

    @Inject
    lateinit var loginService: LoginService

    override fun initContentView(): Int? {
        return R.layout.activity_change_pwd
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        cancel.setOnClickListener {
            finish()
        }
        confirm.setOnClickListener {
            val preferences = App.getLoginSharedPreferences()
            val jsonStr = preferences.getString(LoginService.LOGIN_USER_INFO, "")
            val loginUserInfo = App.objectMapper.readValue(jsonStr, User::class.java)
            thread {
                val resultVo = loginService.changePwd(
                    loginUserInfo.username,
                    password.text.toString(),
                    newPassword.text.toString()
                )
                runOnUiThread {
                    if (!resultVo.success) {
                        if (resultVo.errorCode != AppConsts.LOGIN_ERROR_CODE) {
                            Toast.makeText(this, resultVo.errorMsg, Toast.LENGTH_SHORT).show()
                        }
                        return@runOnUiThread
                    }
                    Toast.makeText(this, getString(R.string.change_pwd_success), Toast.LENGTH_SHORT).show()
                    App.finishAll()
                    LoginActivity.actionStart(this)
                }
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, ChangePwdActivity::class.java)
            context.startActivity(intent)
        }
    }
}
