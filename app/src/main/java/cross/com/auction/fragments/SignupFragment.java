package cross.com.auction.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cross.com.auction.LoginActivity;
import cross.com.auction.database.LoginDataSource;
import cross.com.auction.R;
import cross.com.auction.datamodel.UserInfo;

public class SignupFragment extends Fragment {

    private EditText mEmail;
    private EditText mPwd;
    private EditText mName;
    private EditText mMobileNo;
    private ProgressBar mProgress;
    private LoginDataSource mLoginDataSource;

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        mLoginDataSource = LoginDataSource.getInstance();
        mEmail = (EditText) rootView.findViewById(R.id.signup_email);
        mPwd = (EditText) rootView.findViewById(R.id.signup_password);
        mName = (EditText) rootView.findViewById(R.id.signup_name);
        mMobileNo = (EditText) rootView.findViewById(R.id.signup_mobile_no);
        mProgress = (ProgressBar) rootView.findViewById(R.id.signup_progress_bar);

        Button signup = (Button) rootView.findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPwd.getText().toString();
                String name = mName.getText().toString();
                String mobileNo = mMobileNo.getText().toString();

                if (null != email && !email.isEmpty() &&
                        null != password && !password.isEmpty() &&
                        null != name && !name.isEmpty() &&
                        null != mobileNo && !mobileNo.isEmpty()) {

                    mProgress.setVisibility(View.VISIBLE);
                    Cursor cursor = mLoginDataSource.login(email, password);
                    Log.i(getClass().getSimpleName(), "" + cursor.getCount());
                    if (cursor.getCount() != 0) {
                        String usrId = cursor.getString(0);
                        String pwdDB = cursor.getString(1);
                        if (usrId.equals(email) && pwdDB.contains(password)) {
                            Toast.makeText(getActivity(), R.string.user_id_already_in_use, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    UserInfo userInfo = mLoginDataSource.createEntry(email, password, name, mobileNo);
                    Toast.makeText(getActivity(), R.string.registration_success, Toast.LENGTH_SHORT).show();
                    mProgress.setVisibility(View.INVISIBLE);
                    moveToLoginPage();
                } else {
                    Toast.makeText(getActivity(), R.string.valid_user_details, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button has_account = (Button) rootView.findViewById(R.id.has_account_button);
        has_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginPage();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity) getActivity())
                .setActionBarTitle(getString(R.string.signup_fragment_title));
    }

    private void moveToLoginPage() {
        LoginFragment fragment = new LoginFragment();
        getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment,
                "user_login").addToBackStack("user_login").commit();
    }

}
