package org.javamaster.fragmentlearning.testActivity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animation.*
import org.javamaster.fragmentlearning.R

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        AnimatorInflater.loadAnimator(this, R.animator.property_animation)
            .apply {
                setTarget(image_view)
                start()
            }

        ValueAnimator.ofFloat(0f, 300f, -100f, 200f, -50f, 0f)
            .apply {
                duration = 3000
                repeatCount = -1
                repeatMode = ValueAnimator.REVERSE
                start()
                addUpdateListener {
                    image_view2.translationX = it.animatedValue as Float
                }
            }

        val hyperspaceJump3 = AnimationUtils.loadAnimation(this, R.anim.view_animation)
        image_view3.startAnimation(hyperspaceJump3)

        val hyperspaceJump4 = AnimationUtils.loadAnimation(this, R.anim.view_animation_my)
        image_view4.startAnimation(hyperspaceJump4)

    }
}
