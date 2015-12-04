package cross.com.auction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cross.com.auction.database.AuctionItemsDataSource;
import cross.com.auction.utils.AppConstants;


public class AddItemActivity extends AppCompatActivity {

    private ProgressBar mProgress;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgress.setVisibility(View.INVISIBLE);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().setTitle(R.string.add_item_title);

        Button addItemButton = (Button) findViewById(R.id.add_item_button);
        final EditText itemDesc = (EditText) findViewById(R.id.item_desc);
        final EditText minBidValue = (EditText) findViewById(R.id.minBidValue);
        final EditText auctionDuration = (EditText) findViewById(R.id.auction_duration);
        mProgress = (ProgressBar) findViewById(R.id.add_item_progress);

        mProgress.setVisibility(View.INVISIBLE);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = itemDesc.getText().toString();
                String bidValue = minBidValue.getText().toString();
                String duration = auctionDuration.getText().toString();

                if (!desc.isEmpty() && !bidValue.isEmpty() && !duration.isEmpty()) {
                    if (Integer.MAX_VALUE > Integer.parseInt(bidValue) && Integer.MAX_VALUE > Integer.parseInt(duration)) {
                        if (AppConstants.MAX_AUCTION_DURATION > Integer.parseInt(duration)* 60 * 1000) {
                            AuctionItemsDataSource itemsDB = AuctionItemsDataSource.getInstance();
                            itemsDB.createEntry(desc, Integer.parseInt(bidValue), 0, "empty", System.currentTimeMillis(),
                                    Integer.parseInt(duration) * 60 * 1000, 0);
                            mProgress.setVisibility(View.VISIBLE);
                            mHandler.sendEmptyMessageDelayed(0, 1000);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.max_duration, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.max_value, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.details_are_mandatory, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
