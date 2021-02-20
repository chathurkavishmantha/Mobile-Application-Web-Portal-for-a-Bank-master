/*
package com.karawalaya.alliantbankapp.DAO_SERVICE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CommonDBHelper {
    private Context context = null;
    private DBHelper dbhelper = null;
    public static SQLiteDatabase sqLiteDatabase = null;

    public CommonDBHelper(Context context) {
        this.context = context;
        this.dbhelper = new DBHelper(this.context);
    }

    public SQLiteDatabase open() {
        sqLiteDatabase = this.dbhelper.getWritableDatabase();

        return sqLiteDatabase;
    }

    public void close() {
        if(sqLiteDatabase != null)
            sqLiteDatabase.close();
        if(dbhelper != null)
            dbhelper.close();
    }
}
*/
