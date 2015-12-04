package cross.com.auction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cross.com.auction.datamodel.ItemInfo;
import cross.com.auction.datamodel.ItemManager;
import cross.com.auction.datamodel.UserInfo;
import cross.com.auction.datamodel.UserManager;
import cross.com.auction.utils.AppConstants;
import cross.com.auction.utils.AppPreference;

public class AccountActivity extends AppCompatActivity {

    private TextView mUserId;
    private TextView mUserName;
    private TextView mUserNo;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setTitle(R.string.user_account);

        mUserId = (TextView) findViewById(R.id.accUserId);
        mUserName = (TextView) findViewById(R.id.accUserName);
        mUserNo = (TextView) findViewById(R.id.accUserNo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_acc);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        UserInfo user = UserManager.getInstance().getUserInfo();
        mUserId.setText(user.getEmail());
        mUserName.setText(user.getName());
        mUserNo.setText(user.getNo());

        List<ItemInfo> myItemList = ItemManager.getInstance().getItemInfoList(user.getEmail());
        AccountAdapter adapter = new AccountAdapter(getApplicationContext(), myItemList);
        mRecyclerView.setAdapter(adapter);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference preference = new AppPreference(getApplicationContext());
                preference.setBoolPreference(AppConstants.USER_LOGIN_PREF, false);
                finish();
            }
        });
    }
}
