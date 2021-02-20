package com.karawalaya.alliantbankapp.DAO_SERVICE.user_management;

import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TMQueries;

public class UMQueries {
    public static final String UM_TABLE01 = "online_user";
    public static final String UM_TABLE01_COL01 = "customerId";
    public static final String UM_TABLE01_COL02 = "onlineUserId";
    public static final String UM_TABLE01_COL03 = "userName";
    public static final String UM_TABLE01_COL04 = "userPassword";

    public static final String UM_Query_TABLE01_CREATE = "CREATE TABLE " + UM_TABLE01 + "(" +
            UM_TABLE01_COL01 + " CHAR(10) NOT NULL UNIQUE," +
            UM_TABLE01_COL02 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            UM_TABLE01_COL03 + " TEXT NOT NULL," +
            UM_TABLE01_COL04 + " TEXT NOT NULL," +
            "CONSTRAINT fk01_online_user FOREIGN KEY (" + UM_TABLE01_COL01 + ") REFERENCES " + TMQueries.TM_TABLE01 + " (" + TMQueries.TM_TABLE01_COL01 + ") ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";
    public static final String UM_Query_TABLE01_DROP = "DROP TABLE IF EXISTS " + UM_TABLE01 + ";";
    public static final String UM_Query_TABLE01_INSERT = "INSERT INTO " + UM_TABLE01 + "(" + UM_TABLE01_COL01 + ", " + UM_TABLE01_COL03 + ", " + UM_TABLE01_COL04 + ") VALUES (?, ?, ?);";
    public static final String UM_Query_TABLE01_SELECT_ALL = "SELECT * FROM " + UM_TABLE01 + ";";

    public static final String UM_Query_CHECK_ONLINE_USER = "SELECT * FROM " + UM_TABLE01 + " WHERE " + UM_TABLE01_COL01 + " =?;";
    public static final String UM_Query_VERIFY_ONLINE_USER = "SELECT * FROM " + UM_TABLE01 + " WHERE " + UM_TABLE01_COL03 + " =? AND " + UM_TABLE01_COL04 + " =?;";

    public static final String UM_Query_SEARCH_TRANSACTION_ON_ID = "SELECT * FROM " + TMQueries.TM_TABLE03 + " WHERE " + TMQueries.TM_TABLE03_COL01 + " =?;";
    public static final String UM_Query_REMOVE_USER = "DELETE ";
}