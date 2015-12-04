package cross.com.auction.datamodel;

import android.database.Cursor;

import cross.com.auction.database.LoginDataSource;

/**
 * Created by Arvind on 30-10-2015.
 */
public class UserManager {
    private static UserManager ourInstance = new UserManager();
    private UserInfo mUserInfo;

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    private void setUserInfo(UserInfo info) {
        mUserInfo = info;
    }

    public void setUserInfo(String email, String name, String no) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setName(name);
        userInfo.setNo(no);
        UserManager.getInstance().setUserInfo(userInfo);
    }

    public void updateUserInfo(String userId) {
        Cursor cursor = LoginDataSource.getInstance().getUserInfo(userId);
        cursor.moveToFirst();
        UserInfo newEntry = cursorToEntry(cursor);
        setUserInfo(newEntry);
    }

    public UserInfo cursorToEntry(Cursor cursor) {
        UserInfo entry = new UserInfo();
        entry.setEmail(cursor.getString(0));
        entry.setName(cursor.getString(1));
        entry.setNo(cursor.getString(2));
        return entry;
    }
}
