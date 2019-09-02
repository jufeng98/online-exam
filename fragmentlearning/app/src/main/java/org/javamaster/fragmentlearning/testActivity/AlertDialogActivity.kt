package org.javamaster.fragmentlearning.testActivity

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class AlertDialogActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_alert_dialog
    }

    @OnClick(R.id.alert_button)
    fun showAlertDialog(view: View) {
        var alertDialog = AlertDialog.Builder(this@AlertDialogActivity)
        alertDialog.setTitle("这是警告框")
        alertDialog.setMessage("很重要的信息")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which -> })
        alertDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
        alertDialog.show()
    }
}
