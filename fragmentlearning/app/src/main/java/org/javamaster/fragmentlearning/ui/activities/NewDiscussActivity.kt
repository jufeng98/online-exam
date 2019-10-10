package org.javamaster.fragmentlearning.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.OnClick
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_archive.user_photo
import kotlinx.android.synthetic.main.activity_archive.username
import kotlinx.android.synthetic.main.activity_new_discuss.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.DiscussService
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.ui.viewModel.DiscussViewModel
import org.javamaster.fragmentlearning.utils.ImageUtils
import javax.inject.Inject

class NewDiscussActivity : BaseAppActivity() {

    @Inject
    lateinit var discussViewModel: DiscussViewModel
    @Inject
    lateinit var discussService: DiscussService
    private var newDiscussDisposable: Disposable? = null

    override fun initContentView(): Int? {
        return R.layout.activity_new_discuss
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        supportActionBar!!.title = getString(R.string.new_discuss)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val bitmap = ImageUtils.getUserPhoto()
        if (bitmap != null) {
            user_photo.setImageBitmap(bitmap)
        }
        val userInfo = App.objectMapper.readValue(
            App.getLoginSharedPreferences().getString(LoginService.LOGIN_USER_INFO, ""),
            User::class.java
        )
        username.text = userInfo.username
        discussViewModel.discussFormState.observe(this, Observer { discussFormState ->
            question_input_tip.text = "${question_input.text.toString().length}/128"
            description_input_tip.text = "${description_input.text.toString().length}/1024"
            if (discussFormState.questionError != null) {
                question_input.error = getString(discussFormState.questionError)
            }
            if (discussFormState.relevantTagsError != null) {
                relevant_input.error = getString(discussFormState.relevantTagsError)
            }
        })
        discussViewModel.dataValid.observe(this, Observer { dataValid ->
            if (!dataValid) {
                return@Observer
            }
            newDiscussDisposable = Observable.create<Int> {
                it.onNext(
                    discussService.createDiscussions(
                        question_input.text.toString(),
                        description_input.text.toString(),
                        relevant_input.text.toString()
                    )
                )
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show()
                finish()
            }, {
                OperationListener.fail(it)
            })
            return@Observer
        })
        question_input.afterTextChanged {
            discussViewModel.discussDataChanged(question_input.text.toString(), relevant_input.text.toString())
        }
        description_input.afterTextChanged {
            discussViewModel.discussDataChanged(question_input.text.toString(), relevant_input.text.toString())
        }
        relevant_input.afterTextChanged {
            discussViewModel.discussDataChanged(question_input.text.toString(), relevant_input.text.toString())
        }
    }

    @OnClick(R.id.post)
    fun post() {
        discussViewModel.isDataValid(question_input.text.toString(), relevant_input.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onKeyDown(KeyEvent.KEYCODE_BACK, KeyEvent(KeyEvent.KEYCODE_BACK, 0))
                finish()
            }
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        newDiscussDisposable?.dispose()
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, NewDiscussActivity::class.java)
            context.startActivity(intent)
        }
    }
}
