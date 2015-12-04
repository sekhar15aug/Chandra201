package cross.com.auction;

/**
 * Created by Arvind on 31-10-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cross.com.auction.datamodel.ItemInfo;

/**
 * Created by Arvind on 01-11-2015
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.CustomViewHolder> {
    private Context mContext;
    private List<ItemInfo> mItemInfo;

    public AccountAdapter(Context context, List<ItemInfo> itemList) {
        this.mContext = context;
        this.mItemInfo = itemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_list_adapter, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        ItemInfo item = mItemInfo.get(i);
        customViewHolder.itemDesc.setText(item.getItemDesc());
        customViewHolder.currentBidValue.setText(mContext.getString(R.string.your_bid) + item.getLastBid());
        customViewHolder.userId.setText(mContext.getString(R.string.won_by) + item.getLastUser());
        if (System.currentTimeMillis() - item.getStartTime() > item.getDuration()) {
            customViewHolder.status.setText(mContext.getString(R.string.auction_status) +
                    mContext.getString(R.string.auction_closed));
        } else {
            customViewHolder.status.setText(mContext.getString(R.string.auction_status) +
                    mContext.getString(R.string.auction_going_on));
        }
    }

    @Override
    public int getItemCount() {
        return mItemInfo.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView itemDesc;
        TextView currentBidValue;
        TextView userId;
        TextView status;

        public CustomViewHolder(View itemView) {
            super(itemView);

            itemDesc = (TextView) itemView.findViewById(R.id.accListItemDesc);
            currentBidValue = (TextView) itemView.findViewById(R.id.accItemBidValue);
            userId = (TextView) itemView.findViewById(R.id.accListUserId);
            status = (TextView) itemView.findViewById(R.id.accItemStatus);
        }
    }
}

