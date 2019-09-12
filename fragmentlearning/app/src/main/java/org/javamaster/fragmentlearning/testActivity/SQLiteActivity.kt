package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.testDatabase.MyDatabaseHelper
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import javax.inject.Inject


class SQLiteActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_sqlite
    }

    @Inject
    lateinit var myDatabaseHelper: MyDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    @OnClick(R.id.create_table)
    fun createTable() {
        var db = myDatabaseHelper.writableDatabase
        Toast.makeText(this, "${db.path}", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.insert_data)
    fun insertData() {
        var db = myDatabaseHelper.writableDatabase
        try {
            db.beginTransaction()
            // 类库的辅助方法插入数据
            val values = ContentValues()
            values.put("name", "The Da Vinci Code")
            values.put("author", "Dan Brown")
            values.put("pages", 454)
            values.put("price", 16.96)
            db.insert("Book", null, values)
            values.clear()
            // 原生sql插入数据
            db.execSQL("insert into Book(author,price,pages,name)values('yudong',88,535,'Java高手之路')")
            db.setTransactionSuccessful()
            Toast.makeText(this, "insert data succeed", Toast.LENGTH_LONG).show()
        } finally {
            db.endTransaction()
        }
    }

    @OnClick(R.id.update_data)
    fun updateData() {
        var db = myDatabaseHelper.writableDatabase
        val values = ContentValues()
        values.put("pages", 555)
        db.update("Book", values, "author=?", arrayOf("Dan Brown"))
        values.clear()

        db.execSQL("update Book set pages=666 where author='yudong'")
        Toast.makeText(this, "update data succeed", Toast.LENGTH_LONG).show()
    }

    @OnClick(R.id.del_data)
    fun delData() {
        var db = myDatabaseHelper.writableDatabase
        db.delete("Book", "author=?", arrayOf("Dan Brown"))

        db.execSQL("delete from Book where author='yudong'")
        Toast.makeText(this, "del data succeed", Toast.LENGTH_LONG).show()
    }

    @OnClick(R.id.query_data)
    fun queryData() {
        var db = myDatabaseHelper.readableDatabase
        var cursor = db.query(
            "Book",
            arrayOf("id", "author", "price", "pages", "name"),
            "author in (?,?)",
            arrayOf("Dan Brown", "yudong"),
            null,
            null,
            null
        )
        var str = getData(cursor)
        cursor.close()

        var cursor1 = db.rawQuery("select * from Book where author in (?,?)", arrayOf("Dan Brown", "yudong"))
        var str1 = getData(cursor1)
        cursor1.close()
        Toast.makeText(this, "$str\n$str1", Toast.LENGTH_LONG).show()
    }

    private fun getData(cursor: Cursor): String {
        var stringBuilder = StringBuilder()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            var id = cursor.getInt(cursor.getColumnIndex("id"))
            var author = cursor.getString(cursor.getColumnIndex("author"))
            var price = cursor.getDouble(cursor.getColumnIndex("price"))
            var pages = cursor.getInt(cursor.getColumnIndex("pages"))
            var name = cursor.getString(cursor.getColumnIndex("name"))
            stringBuilder.append("$id ").append("$author ").append("$price ").append("$pages ").append("$name ")
                .append("\r\n")
            cursor.moveToNext()
        }
        return stringBuilder.toString()
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, SQLiteActivity::class.java)
            context.startActivity(intent)
        }
    }
}
