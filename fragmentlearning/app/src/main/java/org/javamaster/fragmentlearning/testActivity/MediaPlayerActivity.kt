package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_media_player.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class MediaPlayerActivity : BaseAppActivity() {

    private var player: MediaPlayer = MediaPlayer()

    override fun initContentView(): Int? {
        return R.layout.activity_media_player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show()
            return
        }
        init()
    }

    private fun init() {
        try {
            val musicFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS),
                "Neon.ogg"
            )
            player.setDataSource(musicFile.path)
        } catch (e: Exception) {
            Toast.makeText(this, "无法加载音频文件", Toast.LENGTH_SHORT).show()
            return
        }
        player.prepare()
        player.setOnCompletionListener {
            Toast.makeText(this, "播放完成", Toast.LENGTH_SHORT).show()
            progressBar3.progress = player.duration / 1000
            it.reset()
            init()
        }
        progressBar3.max = player.duration / 1000
    }

    @OnClick(
        R.id.play_media,
        R.id.pause_media,
        R.id.stop_media
    )
    fun handler(view: View) {
        when (view.id) {
            R.id.play_media -> {
                if (!player.isPlaying) {
                    player.start()
                    startUpdateProgress()
                }
            }
            R.id.pause_media -> {
                if (player.isPlaying) {
                    player.pause()
                }
            }
            R.id.stop_media -> {
                if (player.isPlaying) {
                    progressBar3.progress = 0
                    player.reset()
                    init()
                }
            }
        }
    }

    private fun startUpdateProgress() {
        thread {
            while (player.isPlaying) {
                runOnUiThread {
                    progressBar3.progress = player.currentPosition / 1000
                }
                TimeUnit.SECONDS.sleep(1)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    companion object {
        fun actionStart(context: Activity) {
            val intent = Intent(context, MediaPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }
}
