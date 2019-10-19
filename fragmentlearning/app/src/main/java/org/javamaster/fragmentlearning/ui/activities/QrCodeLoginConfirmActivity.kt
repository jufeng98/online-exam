package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.utils.NetUtils
import kotlin.concurrent.thread

/**
 * 扫码登录确认界面
 *
 * @author yudong
 * @date 2019/10/19
 */
class QrCodeLoginConfirmActivity : BaseAppActivity() {

    lateinit var url: String

    override fun initContentView(): Int? {
        return R.layout.activity_qr_code_login_comfirm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = intent.getStringExtra("url")
    }

    @OnClick(R.id.confirm_login)
    fun qrCodeLogin() {
        thread {
            try {
                NetUtils.postForResponse(url, mapOf())
            } catch (e: Exception) {
                Log.i(this::class.qualifiedName, e.message)
            }
        }
        finish()
    }

    @OnClick(R.id.cancel_login)
    fun cancelLogin() {
        finish()
    }

    companion object {
        fun actionStart(context: Context, url: String) {
            val intent = Intent(context, QrCodeLoginConfirmActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}
