package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_sms.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


class SmsActivity : BaseAppActivity() {

    private lateinit var sendFilter: IntentFilter
    private lateinit var sendStatusReceiver: SendStatusReceiver

    override fun initContentView(): Int? {
        return R.layout.activity_sms
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendFilter = IntentFilter()
        sendFilter.addAction("SENT_SMS_ACTION")
        sendStatusReceiver = SendStatusReceiver()
        registerReceiver(sendStatusReceiver, sendFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(sendStatusReceiver)
    }

    @OnClick(R.id.button14)
    fun send() {
        var number = textView17.text.toString()
        var content = editText3.text.toString()
        var manager = SmsManager.getDefault()
        val sentIntent = Intent("SENT_SMS_ACTION")
        val pi = PendingIntent.getBroadcast(this@SmsActivity, 0, sentIntent, 0)
        manager.sendTextMessage(number, null, content, pi, null)
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, SmsActivity::class.java)
            context.startActivity(intent)
        }
    }

}

internal class SendStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (resultCode == RESULT_OK) {
            // 短信发送成功
            Toast.makeText(
                context, "Send succeeded",
                Toast.LENGTH_LONG
            ).show()
        } else {
            // 短信发送失败
            Toast.makeText(
                context, "Send failed",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
