package cross.com.auction.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Arvind on 31-10-2015.
 */
public class LoginSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "table_login";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PWD = "user_pwd";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_NO = "user_no";

    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_USER_PWD + " TEXT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_NO + " TEXT)" ;

    public LoginSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LoginSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}