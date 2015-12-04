package cross.com.auction.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cross.com.auction.LoginActivity;
import cross.com.auction.R;
import cross.com.auction.database.LoginDataSource;
import cross.com.auction.datamodel.UserManager;
import cross.com.auction.utils.AppConstants;
import cross.com.auction.utils.AppPreference;

public class LoginFragment extends Fragment {

    private EditText mEmail;
    private EditText mPwd;
    private LoginDataSource mLoginDataSource;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginDataSource = LoginDataSource.getInstance();
        mEmail = (EditText) rootView.findViewById(R.id.login_email);
        mPwd = (EditText) rootView.findViewById(R.id.login_password);

        Button login = (Button) rootView.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPwd.getText().toString();

                if (null != email && !email.isEmpty() &&
                        null != password && !password.isEmpty()) {

                    Cursor cursor = mLoginDataSource.login(email, password);
                    if (cursor.getCount() != 0) {
                        String usrId = cursor.getString(0);
                        String pwdDB = cursor.getString(1);
                        String name = cursor.getString(2);
                        String num = cursor.getString(3);
                        if (usrId.equals(email) && pwdDB.equals(password)) {

                            Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                            UserManager.getInstance().setUserInfo(usrId, name, num);

                            AuctionFragment fragment = new AuctionFragment();
                            fragmentTransaction(fragment, "auction_frag");

                            AppPreference preference = new AppPreference(getActivity().getApplicationContext());
                            preference.setBoolPreference(AppConstants.USER_LOGIN_PREF, true);
                            preference.setPreference(AppConstants.LOGIN_USER_ID, email);

                        } else if (!usrId.equals(email) || !pwdDB.equals(password)) {
                            Toast.makeText(getActivity(), R.string.valid_user_name_and_password, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.valid_user_name_and_password, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.valid_user_name_and_password, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button no_login = (Button) rootView.findViewById(R.id.no_login_button);
        no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment fragment = new SignupFragment();
                fragmentTransaction(fragment, "signup_frag");
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity) getActivity())
                .setActionBarTitle(getString(R.string.login_fragment_title));
    }

    private void fragmentTransaction(Fragment fragment, String frag_tag) {
        getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment,
                frag_tag).addToBackStack(frag_tag).commit();
    }
}
