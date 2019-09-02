package org.javamaster.fragmentlearning.testDatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * 修改了表结构,一定要升级版本号GlobalModule里MyDatabaseHelper的版本号
 * @author yudong
 * @date 2019/8/30
 */
class MyDatabaseHelper(val context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        @JvmStatic
        val createBookTable = """
        |create table Book(
        | id integer primary key autoincrement,
        | author text,
        | price real,
        | pages integer,
        | name text
        |)
    """.trimMargin()
        @JvmStatic
        val createCategoryTable = """
        |create table Category (
        |id integer primary key autoincrement,
        |category_name text,
        |category_code integer)
    """.trimMargin()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createBookTable)
        db?.execSQL(createCategoryTable)
        Toast.makeText(context, "create table succeed", Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        UpdateHelper.onUpgrade(db, oldVersion, newVersion)
        Toast.makeText(context, "upgrade db succeed", Toast.LENGTH_LONG).show()
    }
}