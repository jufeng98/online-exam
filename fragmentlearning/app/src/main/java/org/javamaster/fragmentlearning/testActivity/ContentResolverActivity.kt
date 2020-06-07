package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_content_resolver.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


class ContentResolverActivity : BaseAppActivity() {

    lateinit var adapter: ArrayAdapter<String>
    private var contacts = mutableListOf<String>()

    override fun initContentView(): Int? {
        return R.layout.activity_content_resolver
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        contacts_view.adapter = adapter
        readContacts()
    }

    private fun readContacts() {
        Log.i(this::class.qualifiedName, ContactsContract.CommonDataKinds.Phone.CONTENT_URI.toString())
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        cursor!!.moveToFirst()
        while (!cursor.isAfterLast) {
            val displayName = cursor.getString(
                cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
            )
            val number = cursor.getString(
                cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
            )
            contacts.add("姓名:$displayName\r\n号码:$number")
            cursor.moveToNext()
        }
        cursor.close()
    }

    companion object {
        fun actionStart(context: Activity) {
            val intent = Intent(context, ContentResolverActivity::class.java)
            context.startActivity(intent)
        }
    }
}
