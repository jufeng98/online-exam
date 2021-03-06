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
    private lateinit var player: VideoView

    override fun initContentView(): Int? {
        return R.layout.activity_video_player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = video_view
        init()
    }

    private fun init() {
        val musicFile =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "media.mp4"
            )
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
                if (!player.isPlaying) {
                    player.start()
                }
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
        player.suspend()
    }

    companion object {
        fun actionStart(context: Activity) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }
}
