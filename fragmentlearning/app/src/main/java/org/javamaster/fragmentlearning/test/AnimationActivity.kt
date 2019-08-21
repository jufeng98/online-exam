package org.javamaster.fragmentlearning.test

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.javamaster.fragmentlearning.R

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val set: Animator? = AnimatorInflater.loadAnimator(this, R.animator.alpha)
            .apply {
                setTarget(imageView2)
                start()
            }

        val image: ImageView = imageView
        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(this, R.anim.alpha)
        image.startAnimation(hyperspaceJump)
    }
}
