package org.javamaster.fragmentlearning.testProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import org.javamaster.fragmentlearning.data.entity.Book
import org.litepal.LitePal


class MyContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "org.javamaster.fragmentlearning.provider"
        private const val TABLE1_DIR = 0
        private const val TABLE1_ITEM = 1
        var uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            // 查询所有数据
            addURI(AUTHORITY, "book", TABLE1_DIR)
            // 查询单条数据
            addURI(AUTHORITY, "book/#", TABLE1_ITEM)
        }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val bookId = uri.pathSegments[1]
        return LitePal.delete(Book::class.java, bookId.toLong())
    }

    //    1. 必须以 vnd 开头。
    //    2. 如果内容 URI 以路径结尾，则后接 android.cursor.dir/，如果内容 URI 以 id 结尾，
    //    则后接 android.cursor.item/。
    //    3. 最后接上 vnd.<authority>.<path>。
    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            TABLE1_DIR -> "vnd.android.cursor.dir/vnd.$AUTHORITY.book"
            TABLE1_ITEM -> "vnd.android.cursor.item/vnd.$AUTHORITY.book"
            else -> {
                null
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val book = Book()
        book.author = values!!["author"] as String
        book.name = values["name"] as String
        book.pages = values["pages"] as Int
        book.price = values["price"] as Double
        book.save()
        return Uri.parse("content://$AUTHORITY/book/${book.id}")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            TABLE1_DIR -> {
                LitePal.findBySQL("select * from book")
            }
            TABLE1_ITEM -> {
                val bookId = uri.pathSegments[1]
                LitePal.findBySQL("select * from book where id = ?", bookId)
            }
            else -> {
                null
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return LitePal.getDatabase().update("book", values, selection, selectionArgs)
    }
}
