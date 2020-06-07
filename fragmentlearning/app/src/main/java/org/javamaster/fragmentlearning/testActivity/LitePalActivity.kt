package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.Book
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import org.litepal.LitePal
import org.litepal.extension.findAllAsync

class LitePalActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_litepal
    }

    @OnClick(R.id.create_table_copy)
    fun createTable() {
        LitePal.getDatabase()
        Toast.makeText(this, "创建数据库成功", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.insert_data_copy)
    fun insertData() {
        val book = Book(0, "yudong", 88.8, 678, "Java高手之路", "")
        book.save()
        book.pages = 666
        book.save()
        Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.update_data_copy)
    fun updateData() {
        val book = Book()
        book.pages = 888
        book.saveOrUpdate("author=?", "yudong")
        Toast.makeText(this, "更新数据成功", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.del_data_copy)
    fun delData() {
        LitePal.delete(Book::class.java, 1)
        LitePal.deleteAll("book", "author=?", "yudong")
        Toast.makeText(this, "删除数据成功", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.query_data_copy)
    fun queryData() {
        val list = LitePal.findAll(Book::class.java)
        Toast.makeText(this, list.joinToString(), Toast.LENGTH_SHORT).show()
        val books = LitePal.where("author=?", "yudong").order("id").find(Book::class.java)
        Toast.makeText(this, books.toString(), Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.data_async)
    fun async() {
        LitePal.findAllAsync<Book>().listen {
            Toast.makeText(this@LitePalActivity, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun actionStart(context: Activity) {
            val intent = Intent(context, LitePalActivity::class.java)
            context.startActivity(intent)
        }
    }
}
