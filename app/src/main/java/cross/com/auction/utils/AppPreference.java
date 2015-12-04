package cross.com.auction.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arvind on 30-10-2015.
 */
public class AppPreference {
    private static String APP_PREFERENCES = "MY_APP_PREFERENCES";
    private SharedPreferences mAppPreference;

    public AppPreference(Context context) {
        mAppPreference = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setPreference(String key, String value) {
        SharedPreferences.Editor edit = mAppPreference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getPreference(String key) {
        return mAppPreference.getString(key, null);
    }

    public void setBoolPreference(String key, Boolean value) {
        SharedPreferences.Editor edit = mAppPreference.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public Boolean getBoolPreference(String key) {
        return mAppPreference.getBoolean(key, false);
    }
}
