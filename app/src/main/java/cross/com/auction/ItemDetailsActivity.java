package cross.com.auction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cross.com.auction.database.AuctionItemsDataSource;
import cross.com.auction.datamodel.ItemInfo;
import cross.com.auction.datamodel.ItemManager;
import cross.com.auction.datamodel.UserInfo;
import cross.com.auction.datamodel.UserManager;
import cross.com.auction.utils.AppConstants;
import cross.com.auction.utils.AppUtils;

public class ItemDetailsActivity extends AppCompatActivity {

    private int mItemPosition;
    private AuctionItemsDataSource mItemsDB;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getBaseContext(), "your bid is success!", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_item);

        mItemsDB = AuctionItemsDataSource.getInstance();

        ImageView imageView = (ImageView) findViewById(R.id.itemImageView);
        mItemPosition = getIntent().getIntExtra(AppConstants.ITEM_POSITION, -1);

        if (mItemPosition != -1) {
            if (mItemPosition % 3 == 0) {
                imageView.setImageResource(R.drawable.tesla);
            } else if (mItemPosition % 3 == 1) {
                imageView.setImageResource(R.drawable.tesla2);
            } else if (mItemPosition % 3 == 2) {
                imageView.setImageResource(R.drawable.tesla3);
            }
        }

        final ItemInfo item = ItemManager.getInstance().getCurrentItem(mItemPosition);

        TextView auctionItemDesc = (TextView) findViewById(R.id.auction_item_desc);
        TextView currentBid = (TextView) findViewById(R.id.itemCurrentBid);
        TextView timeLeft = (TextView) findViewById(R.id.itemTimeLeft);
        final EditText newBidValue = (EditText) findViewById(R.id.newBidValue);
        Button bidButton = (Button) findViewById(R.id.bidButton);

        auctionItemDesc.setText(item.getItemDesc());
        final int maxBid;
        if (item.getLastBid() > item.getMinBid()) {
            currentBid.setText(getString(R.string.current_bid_text) + String.valueOf(item.getLastBid()));
            maxBid = item.getLastBid();
        } else {
            currentBid.setText(getString(R.string.current_bid_text) + String.valueOf(item.getMinBid()));
            maxBid = item.getMinBid();
        }
        if (System.currentTimeMillis() - item.getStartTime() > item.getDuration()) {
            timeLeft.setText(R.string.auction_closed);
        } else {
            timeLeft.setText(getString(R.string.time_left) +
                    AppUtils.getAuctionTimeLeft(item.getDuration() - (System.currentTimeMillis() - item.getStartTime())));
        }

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (item.getDuration() - (System.currentTimeMillis() - item.getStartTime()) > 0) {
                        String newBid = newBidValue.getText().toString();
                        if (!newBid.isEmpty() && Integer.parseInt(newBid) > maxBid) {
                            if (Integer.MAX_VALUE > Integer.parseInt(newBid)) {
                                UserInfo user = UserManager.getInstance().getUserInfo();
                                mItemsDB.updateEntry(item.getItemId(), Integer.parseInt(newBid),
                                        user.getEmail(), item.getTotalBids() + 1);
                                mHandler.sendEmptyMessageDelayed(0, 200);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.max_value, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.auction_new_bid_value, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.auction_already_closed, Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ex) {
                    Toast.makeText(getApplicationContext(), R.string.max_value, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
