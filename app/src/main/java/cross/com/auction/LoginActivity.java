package cross.com.auction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cross.com.auction.datamodel.UserManager;
import cross.com.auction.fragments.AuctionFragment;
import cross.com.auction.fragments.LoginFragment;
import cross.com.auction.utils.AppConstants;
import cross.com.auction.utils.AppPreference;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppPreference preference = new AppPreference(getApplicationContext());
        if (!preference.getBoolPreference(AppConstants.USER_LOGIN_PREF)) {
            LoginFragment fragment = new LoginFragment();
            getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
        } else {
            String userId = preference.getPreference(AppConstants.LOGIN_USER_ID);
            if (userId != null && UserManager.getInstance().getUserInfo() == null) {
                UserManager.getInstance().updateUserInfo(userId);
            }
            AuctionFragment fragment = new AuctionFragment();
            getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0 ){
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
