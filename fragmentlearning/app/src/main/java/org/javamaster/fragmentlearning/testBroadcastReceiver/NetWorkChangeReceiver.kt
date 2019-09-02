package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import org.javamaster.fragmentlearning.common.App

/**
 * @author yudong
 * @date 2019/8/28
 */
class NetWorkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var connManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo = connManager.activeNetworkInfo
        Log.i(this::javaClass.name, context.javaClass.name)
        Log.i(this::javaClass.name, App.context?.javaClass?.name)
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable) {
            Toast.makeText(context, "net work available", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "net work unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}