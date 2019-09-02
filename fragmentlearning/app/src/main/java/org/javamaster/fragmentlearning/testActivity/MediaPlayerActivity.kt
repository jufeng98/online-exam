package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.File


class MediaPlayerActivity : BaseAppActivity() {
    lateinit var player: MediaPlayer
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
        var musicFile = File(Environment.getExternalStorageDirectory(), "黄昏里.mp3")
        player = MediaPlayer()
        player.setDataSource(musicFile.path)
        player.prepare()
    }

    @OnClick(
        R.id.play_media,
        R.id.pause_media,
        R.id.stop_media
    )
    fun handler(view: View) {
        when (view.id) {
            R.id.play_media -> {
                Thread {
                    if (!player.isPlaying) {
                        player.start()
                    }
                }.start()
            }
            R.id.pause_media -> {
                if (player.isPlaying) {
                    player.pause()
                }
            }
            R.id.stop_media -> {
                if (player.isPlaying) {
                    player.reset()
                    init()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player.stop()
            player.release()
        }
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, MediaPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }
}
