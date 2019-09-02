package org.javamaster.fragmentlearning.testDatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author yudong
 * @date 2019/8/30
 */
public class UpdateHelper {
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 使用这种方式来维护数据库的升级，不管版本怎样更新，都可以保证数据库的表结构是
        // 最新的，而且表中的数据也完全不会丢失,注意,没有break,保证跨版本升级也不会出现问题
        switch (oldVersion) {
            case 1:
                db.execSQL(MyDatabaseHelper.getCreateCategoryTable());
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }
}
