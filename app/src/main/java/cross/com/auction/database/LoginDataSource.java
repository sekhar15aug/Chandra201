package cross.com.auction.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cross.com.auction.datamodel.UserInfo;
import cross.com.auction.datamodel.UserManager;

/**
 * Created by Arvind on 31-10-2015.
 */
public class LoginDataSource {

    private static Context mContext;
    private SQLiteDatabase database;
    private LoginSQLiteHelper dbHelper;

    private String[] allColumns = {LoginSQLiteHelper.COLUMN_ID,
            LoginSQLiteHelper.COLUMN_USER_EMAIL,
            LoginSQLiteHelper.COLUMN_USER_PWD,
            LoginSQLiteHelper.COLUMN_USER_NAME,
            LoginSQLiteHelper.COLUMN_USER_NO};

    private static LoginDataSource ourInstance = null;

    public static LoginDataSource getInstance() {
        if (null == ourInstance) {
            ourInstance = new LoginDataSource();
        }
        return ourInstance;
    }

    public void setAppContext(Context context) {
        mContext = context;
    }

    private LoginDataSource() {
    }

    public void open() throws SQLException {
        dbHelper = new LoginSQLiteHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<UserInfo> getAllEntry() {
        List<UserInfo> entryList = new ArrayList<>();

        Cursor cursor = database.query(LoginSQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserInfo entry = UserManager.getInstance().cursorToEntry(cursor);
            entryList.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return entryList;
    }

    public UserInfo createEntry(String email, String pwd, String name,
                                String no) {
        ContentValues values = new ContentValues();
        values.put(LoginSQLiteHelper.COLUMN_USER_EMAIL, email);
        values.put(LoginSQLiteHelper.COLUMN_USER_PWD, pwd);
        values.put(LoginSQLiteHelper.COLUMN_USER_NAME, name);
        values.put(LoginSQLiteHelper.COLUMN_USER_NO, no);
        long insertId = database.insert(LoginSQLiteHelper.TABLE_NAME, null, values);

        Cursor cursor = database.query(LoginSQLiteHelper.TABLE_NAME,
                allColumns, LoginSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserInfo newEntry = UserManager.getInstance().cursorToEntry(cursor);
        cursor.close();
        return newEntry;
    }

    public void deleteEntry(UserInfo entry) {
        String id = entry.getEmail();
        database.delete(LoginSQLiteHelper.TABLE_NAME, LoginSQLiteHelper.COLUMN_USER_EMAIL
                + " = " + id, null);
    }

    public Cursor login(String userId, String password) {
        Cursor myCursor = database.query(LoginSQLiteHelper.TABLE_NAME,
                new String[]{LoginSQLiteHelper.COLUMN_USER_EMAIL, LoginSQLiteHelper.COLUMN_USER_PWD,
                        LoginSQLiteHelper.COLUMN_USER_NAME, LoginSQLiteHelper.COLUMN_USER_NO},
                LoginSQLiteHelper.COLUMN_USER_EMAIL + "='" + userId + "' AND " +
                        LoginSQLiteHelper.COLUMN_USER_PWD + "='" + password + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    public Cursor getUserInfo(String userId) {
        Cursor myCursor = database.query(LoginSQLiteHelper.TABLE_NAME,
                new String[]{LoginSQLiteHelper.COLUMN_USER_EMAIL,
                        LoginSQLiteHelper.COLUMN_USER_NAME, LoginSQLiteHelper.COLUMN_USER_NO},
                LoginSQLiteHelper.COLUMN_USER_EMAIL + "='" + userId + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }
}
