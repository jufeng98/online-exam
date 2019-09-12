package org.javamaster.fragmentlearning.testCompleteDown

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class DownloadActivity : BaseAppActivity() {
    lateinit var downloadBinder: DownloadService.DownloadBinder
    var conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Toast.makeText(this@DownloadActivity, "服务已停止", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Toast.makeText(this@DownloadActivity, "服务已启动", Toast.LENGTH_SHORT).show()
            downloadBinder = service as DownloadService.DownloadBinder
        }

    }

    override fun initContentView(): Int? {
        return R.layout.activity_download
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = Intent(this, DownloadService::class.java)
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    @OnClick(R.id.start_download)
    fun startDownload() {
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show()
        downloadBinder.startDownload("http://cr1.197946.com/baiduyunguanjria_6.7.4.zip")
    }

    @OnClick(R.id.pause_download)
    fun pauseDownload() {
        downloadBinder.pauseDownload()
    }

    @OnClick(R.id.stop_download)
    fun stopDownload() {
        downloadBinder.stopDownload()
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadBinder.stopDownload()
        unbindService(conn)
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, DownloadActivity::class.java)
            context.startActivity(intent)
        }
    }
}
