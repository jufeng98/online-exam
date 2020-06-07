package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * @author yudong
 * @date 2019/8/28
 */
class NetWorkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable) {
            Toast.makeText(context, "net work available", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "net work unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}