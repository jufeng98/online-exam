package org.javamaster.fragmentlearning.testActivity

import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class AlertDialogActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_alert_dialog
    }

    @OnClick(R.id.alert_button)
    fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("这是警告框")
//        alertDialog.setMessage("很重要的信息")
        alertDialog.setItems(arrayOf("Apple", "Orange")) { _, which ->
            Toast.makeText(this, "you click $which", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setPositiveButton("OK") { _, which ->
            Toast.makeText(this, "you click $which", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setNegativeButton("Cancel") { _, which ->
            Toast.makeText(this, "you click $which", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    @OnClick(R.id.button16)
    fun showActivityAlertDialog() {
        val intent = Intent(this, LoginInvalidActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.alert_button1)
    fun showAlertDialog1() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("这是进度条警告框")
        progressDialog.setMessage("加载中")
        progressDialog.setCancelable(true)
        progressDialog.show()
    }
}
