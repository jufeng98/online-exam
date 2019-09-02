package org.javamaster.fragmentlearning.testActivity

import android.app.ProgressDialog
import android.view.View
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class ProgressDialogActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_progress_dialog
    }

    @OnClick(R.id.alert_button)
    fun showAlertDialog(view: View) {
        var progressDialog: ProgressDialog = ProgressDialog(this)
        progressDialog.setTitle("这是进度警告框")
        progressDialog.setMessage("加载中")
        progressDialog.setCancelable(true)
        progressDialog.show()
    }
}
