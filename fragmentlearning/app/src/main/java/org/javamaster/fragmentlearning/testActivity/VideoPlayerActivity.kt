package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.VideoView
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_video_player.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.File

class VideoPlayerActivity : BaseAppActivity() {
    lateinit var player: VideoView

    override fun initContentView(): Int? {
        return R.layout.activity_video_player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = video_view
        init()
    }

    private fun init() {
        var musicFile = File(Environment.getExternalStorageDirectory(), "boss_before.wmv")
        player.setVideoPath(musicFile.path)
    }

    @OnClick(
        R.id.button5,
        R.id.button6,
        R.id.button7
    )
    fun handler(view: View) {
        when (view.id) {
            R.id.button5 -> {
                Thread {
                    if (!player.isPlaying) {
                        player.start()
                    }
                }.start()
            }
            R.id.button6 -> {
                if (player.isPlaying) {
                    player.pause()
                }
            }
            R.id.button7 -> {
                if (player.isPlaying) {
                    player.resume()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player.suspend()
        }
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, VideoPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }
}
