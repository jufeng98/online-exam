package org.javamaster.fragmentlearning.testActivity

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animation_more.*
import org.javamaster.fragmentlearning.R

class AnimationMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_more)
        var rgbAnimator: ObjectAnimator? = null
        button19.setOnClickListener {
            rgbAnimator = ObjectAnimator.ofInt(animation_layout, "backgroundColor", 0x00ff0000, 0x6600ff00, 0x770000ff)
                .apply {
                    setEvaluator(ArgbEvaluator())
                    repeatCount = -1
                    repeatMode = ValueAnimator.REVERSE
                    duration = 3000
                    start()
                }
        }
        button21.setOnClickListener {
            rgbAnimator?.cancel()
        }
    }
}
