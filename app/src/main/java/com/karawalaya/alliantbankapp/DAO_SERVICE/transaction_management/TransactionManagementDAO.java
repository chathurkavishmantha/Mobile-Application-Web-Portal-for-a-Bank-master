package com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.karawalaya.alliantbankapp.DAO_SERVICE.DBHelper;
import com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UMQueries;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.DateConverter;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TransactionManagementDAO {
    private DBHelper dbhelper;

    public TransactionManagementDAO(Context context) {
        this.dbhelper = new DBHelper(context);
    }

    public Account getAccountDetails(Customer customer) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {customer.getCustomerId()};

        Cursor cursor = sqLiteDatabase.rawQuery(TMQueries.TM_Query_TABLE02_SELECT_SPECIFIC, selectionArgs);

        Account account = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = new Account(cursor.getString(0), cursor.getInt(1), cursor.getString(2), cursor.getDouble(3));
        }
        return account;
    }

    public Collection<Transaction> getTransactionHistory(int creditAccount) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {Integer.toString(creditAccount)};

        Cursor cursor = sqLiteDatabase.rawQuery(TMQueries.TM_Query_TABLE03_SELECT_SPECIFICS, selectionArgs);
        Transaction transaction = null;
        Collection<Transaction> transactionList = new ArrayList<Transaction>();
        int i = 0;

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                transaction = new Transaction(cursor.getInt(0), cursor.getInt(1), DateConverter.getSqlDateFromString(cursor.getString(2)), cursor.getDouble(3), cursor.getInt(4));
                transactionList.add(transaction);
                i++;
            }
        }
        sqLiteDatabase.close();
        if(i == 0)
            transactionList = null;

        return transactionList;
    }

    public boolean makeATransaction(Account account, int debitAccount, double transferAmount) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        SQLiteStatement st01 = null;
        st01 = sqLiteDatabase.compileStatement(TMQueries.TM_Query_TABLE03_INSERT);

        st01.bindLong(1, account.getAccountNo());
        st01.bindString(2, DateConverter.getCurrentSqlDate().toString());
        st01.bindDouble(3, transferAmount);
        st01.bindLong(4, debitAccount);

        long rowId01 = st01.executeInsert();

        if (rowId01 == -1)
            return false;
        else {
            if(creditToAccount(account.getAccountNo(), transferAmount)) {
                Log.e("Kara", "=======================CREDITING TO ACCOUNT SUCCESSFUL=======================");
                if(debitToAccount(debitAccount, transferAmount)) {
                    Log.e("Kara", "=======================DEBITING TO ACCOUNT SUCCESSFUL=======================");
                    return true;
                } else {
                    Log.e("Kara", "=======================DEBITING TO ACCOUNT UNSUCCESSFUL=======================");
                    return false;
                }
            } else {
                Log.e("Kara", "=======================CREDITING TO ACCOUNT UNSUCCESSFUL=======================");
                return false;
            }
        }
    }

    public boolean creditToAccount(int creditAccount, double transferAmount) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        SQLiteStatement st01 = null;
        st01 = sqLiteDatabase.compileStatement(TMQueries.TM_Query_TABLE02_UPDATE_BALANCE_CREDIT);
        st01.bindDouble(1, transferAmount);
        st01.bindLong(2, creditAccount);

        long rowId01 = st01.executeUpdateDelete();

        if(rowId01 == -1)
            return false;
        else
            return true;

    }

    public boolean debitToAccount(int debitAccount, double transferAmount) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        SQLiteStatement st01 = null;
        st01 = sqLiteDatabase.compileStatement(TMQueries.TM_Query_TABLE02_UPDATE_BALANCE_DEBIT);
        st01.bindDouble(1, transferAmount);
        st01.bindLong(2, debitAccount);

        long rowId01 = st01.executeUpdateDelete();

        sqLiteDatabase.close();
        if(rowId01 == -1)
            return false;
        else
            return true;
    }

    public Collection<Transaction> getTransactionDetails(int creditAccount, int debitAccount) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {Integer.toString(creditAccount), Integer.toString(debitAccount)};

        Cursor cursor = sqLiteDatabase.rawQuery(TMQueries.TM_Query_TM_TABLE03_SELECT_SEARCH, selectionArgs);
        Transaction transaction = null;
        Collection<Transaction> transactionList = new ArrayList<Transaction>();
        int i = 0;

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                transaction = new Transaction(cursor.getInt(0), cursor.getInt(1), DateConverter.getSqlDateFromString(cursor.getString(2)), cursor.getDouble(3), cursor.getInt(4));
                transactionList.add(transaction);
                i++;
            }
        }
        sqLiteDatabase.close();
        if(i == 0)
            transactionList = null;

        return transactionList;
    }

    public boolean removeTransaction(int transactionId, Account account) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String[] selectionArgs = {Integer.toString(transactionId)};
        Transaction trans = null;
        Cursor cursor = sqLiteDatabase.rawQuery(TMQueries.TM_Query_TM_TABLE03_SELECT_SEARCH_USING_TRAN_ID, selectionArgs);
        int i = 0;

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            int creditAccount = cursor.getInt(1);

            if(creditAccount == account.getAccountNo()) {
                i = sqLiteDatabase.delete(TMQueries.TM_TABLE03, TMQueries.TM_TABLE03_COL01 + "=?", selectionArgs);
            }
        }

        sqLiteDatabase.close();

        if(i == 0)
            return false;
        else
            return true;
    }
}
