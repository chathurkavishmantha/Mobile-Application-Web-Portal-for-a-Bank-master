/*
package com.karawalaya.alliantbankapp.DAO_SERVICE.user_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.karawalaya.alliantbankapp.DAO_SERVICE.CommonDBHelper;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.User;

public class UserManagementDAO {
    private CommonDBHelper commonDBHelper = null;

    public UserManagementDAO(Context context) {
        this.commonDBHelper = new CommonDBHelper(context);
    }

    public void addUSer(User user) {
        SQLiteDatabase sqLiteDatabase = commonDBHelper.open();

        ContentValues values = new ContentValues();
        values.put(UMQueries.UM_TABLE01_COL02, user.getName());
        values.put(UMQueries.UM_TABLE01_COL03, user.getEmail());
        values.put(UMQueries.UM_TABLE01_COL04, user.getPassword());

        sqLiteDatabase.insert(UMQueries.UM_TABLE01, null, values);
        commonDBHelper.close();
    }

    public boolean checkUSer(String email) {
        String[] columns = {
                UMQueries.UM_TABLE01_COL01
        };
        SQLiteDatabase sqLiteDatabase = commonDBHelper.open();
        String selection = UMQueries.UM_TABLE01_COL03 + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = sqLiteDatabase.query(UMQueries.UM_TABLE01, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        commonDBHelper.close();

        if(cursorCount > 0)
            return true;
        return false;
    }

    public boolean checkUSser(String email, String password) {
        String[] columns = {
                UMQueries.UM_TABLE01_COL01
        };
        SQLiteDatabase sqLiteDatabase = commonDBHelper.open();
        String selection = UMQueries.UM_TABLE01_COL03 + "=? AND " + UMQueries.UM_TABLE01_COL04 + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = sqLiteDatabase.query(UMQueries.UM_TABLE01, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        commonDBHelper.close();

        if(cursorCount > 0)
            return true;
        return false;
    }
}
*/
package com.karawalaya.alliantbankapp.DAO_SERVICE.user_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.karawalaya.alliantbankapp.DAO_SERVICE.DBHelper;
import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TMQueries;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.DateConverter;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Transaction;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.OnlineUser;

import static com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UMQueries.UM_TABLE01;
import static com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UMQueries.UM_TABLE01_COL04;

public class UserManagementDAO {
    private DBHelper dbhelper;

    public UserManagementDAO(Context context) {
//        context.deleteDatabase("mad_2018_g3_07.db");
        this.dbhelper = new DBHelper(context);
    }

    public String seeWhetherPhysicallyRegistered(String email, String accNo) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {email, accNo};
        Cursor cursor = sqLiteDatabase.rawQuery(TMQueries.TM_Query_UM_FIND, selectionArgs);

        String customerId = null;
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                if(cursor.getString(10).equals(email) && cursor.getLong(12) == Integer.parseInt(accNo)) {
                    customerId = cursor.getString(0);
                    break;
                }
            }
        }
        sqLiteDatabase.close();

        return customerId;
    }

    public boolean registerOnlineUser(OnlineUser onlineUser) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        boolean bool;

        ContentValues values = new ContentValues();
        values.put(UMQueries.UM_TABLE01_COL01, onlineUser.getCustomerId());
        values.put(UMQueries.UM_TABLE01_COL03, onlineUser.getUserName());
        values.put(UM_TABLE01_COL04, onlineUser.getPassword());

        long result = sqLiteDatabase.insert(UM_TABLE01, null, values);

        if(result == -1)
            bool = false;
        else
            bool = true;

        sqLiteDatabase.close();
        return bool;
    }

   public boolean checkUser(String email) {
        String[] columns = {
                UMQueries.UM_TABLE01_COL01
        };
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String selection = UMQueries.UM_TABLE01_COL03 + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = sqLiteDatabase.query(UMQueries.UM_TABLE01, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if(cursorCount > 0)
            return true;
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {
                UMQueries.UM_TABLE01_COL01
        };
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String selection = UMQueries.UM_TABLE01_COL03 + "=? AND " + UMQueries.UM_TABLE01_COL04 + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = sqLiteDatabase.query(UMQueries.UM_TABLE01, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if(cursorCount > 0)
            return true;
        return false;
    }

    public boolean alreadyAnOnlineUser(String customerId) {
        boolean bool = false;
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {customerId};
        Cursor cursor = sqLiteDatabase.rawQuery(UMQueries.UM_Query_CHECK_ONLINE_USER, selectionArgs);

        if(cursor.getCount()>0)
            bool = true;

        sqLiteDatabase.close();

        return bool;
    }

    public Customer login(String username, String password) {
        Customer customer = null;
        OnlineUser onlineUser = null;
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs01 = {username, password};
        Cursor cursor01 = sqLiteDatabase.rawQuery(UMQueries.UM_Query_VERIFY_ONLINE_USER, selectionArgs01);

        if(cursor01.getCount() > 0) {
            while(cursor01.moveToNext()) {
                if(cursor01.getString(2).equals(username) && cursor01.getString(3).equals(password)) {
                    onlineUser = new OnlineUser();
                    onlineUser.setCustomerId(cursor01.getString(0));
                    onlineUser.setOnlineUserId(cursor01.getInt(1));
                    onlineUser.setUserName(cursor01.getString(2));
                    //For security reasons... password is not set!

                    break;
                }
            }

            /**
             * @Author Samarasekara S.A.M.I.D.
             */
            if(onlineUser != null) {
                String[] selectionArgs02 = {onlineUser.getCustomerId()};
                    Cursor cursor02 = sqLiteDatabase.rawQuery(TMQueries.TM_Query_GET_CUSTOMER, selectionArgs02);
                    if(cursor02.getCount() > 0) {
                        cursor02.moveToNext();
                        customer = new Customer(cursor02.getString(0), cursor02.getString(1), cursor02.getString(2), cursor02.getInt(3), cursor02.getString(4),
                                cursor02.getString(5), cursor02.getString(6), cursor02.getString(7), cursor02.getInt(8), cursor02.getString(9), cursor02.getString(10));
                    customer.setOnlineUser(onlineUser);
                }
            }
            /**
             * @Author Samarasekara S.A.M.I.D.
             */
        }
        sqLiteDatabase.close();

        return customer;
    }

    public Transaction getTransaction(int TransactionId, Account account) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {Integer.toString(TransactionId)};
        Transaction transaction = null;
        Cursor cursor = sqLiteDatabase.rawQuery(UMQueries.UM_Query_SEARCH_TRANSACTION_ON_ID, selectionArgs);

        if(cursor.getCount() > 0) {
            cursor.moveToNext();
            transaction = new Transaction(cursor.getInt(0), cursor.getInt(1), DateConverter.getSqlDateFromString(cursor.getString(2)), cursor.getDouble(3), cursor.getInt(4));
        }

        if(transaction != null) {
            if(transaction.getCreditAccount() != account.getAccountNo())
                transaction = null;
        }

        return transaction;
    }

    public void updatePassword(String email, String password){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UM_TABLE01_COL04, password);
        //db.update(UM_TABLE01 , values, COLUMN_USER_EMAIL+" = ?",new String[] { email });
        db.update(UM_TABLE01, values, UM_TABLE01_COL04 + " = ?" , new String[]{ email} );
        db.close();
    }

    public boolean removeUser(String customerId) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {customerId};

        int i = sqLiteDatabase.delete(TMQueries.TM_TABLE01, TMQueries.TM_TABLE01_COL01 + "=?", selectionArgs);

        sqLiteDatabase.close();

        if(i == 0)
            return false;
        else
            return true;
    }
}