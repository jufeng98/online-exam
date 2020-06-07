package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast


class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        val pdus = bundle?.get("pdus") as Array<*> // 提取短信消息
        val messages = arrayOfNulls<SmsMessage>(pdus.size)
        for (i in messages.indices) {
            messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
        }
        val address = messages[0]!!.originatingAddress // 获取发送方号码
        var fullMessage = ""
        for (message in messages) {
            fullMessage += message!!.messageBody // 获取短信内容
        }
        abortBroadcast()//阻断广播
        Toast.makeText(context, "address:$address,content:$fullMessage", Toast.LENGTH_LONG).show()
    }

}
