package cross.com.auction;

/**
 * Created by Arvind on 31-10-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cross.com.auction.datamodel.ItemInfo;
import cross.com.auction.interfaces.ItemClickListener;
import cross.com.auction.utils.AppUtils;

/**
 * Created by Arvind on 15-08-2015.
 */

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.CustomViewHolder> {
    private Context mContext;
    private List<ItemInfo> mItemInfo;
    private ItemClickListener mListener;

    public AuctionAdapter(Context context, List<ItemInfo> itemList,
                          ItemClickListener listener) {
        this.mContext = context;
        this.mItemInfo = itemList;
        mListener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.auction_list_adapter, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        ItemInfo item = mItemInfo.get(i);

        if (i % 3 == 0) {
            customViewHolder.imageView.setImageResource(R.drawable.tesla);
        } else if (i % 3 == 1) {
            customViewHolder.imageView.setImageResource(R.drawable.tesla2);
        } else if (i % 3 == 2) {
            customViewHolder.imageView.setImageResource(R.drawable.tesla3);
        }

        customViewHolder.itemDesc.setText(item.getItemDesc());
        if (item.getMinBid() > item.getLastBid()) {
            customViewHolder.currentBidValue.setText(mContext.getString(R.string.current_bid_text) + item.getMinBid());
        }else{
            customViewHolder.currentBidValue.setText(mContext.getString(R.string.current_bid_text) + item.getLastBid());
        }
        customViewHolder.totalBids.setText(item.getTotalBids() + " bids");
        if (System.currentTimeMillis() - item.getStartTime() > item.getDuration()) {
            customViewHolder.timeLeft.setText(R.string.auction_closed);
        } else {
            customViewHolder.timeLeft.setText(mContext.getString(R.string.time_left) +
                    AppUtils.getAuctionTimeLeft(item.getDuration() - (System.currentTimeMillis() - item.getStartTime())));
        }
    }

    @Override
    public int getItemCount() {
        return mItemInfo.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView itemDesc;
        TextView currentBidValue;
        TextView totalBids;
        TextView timeLeft;

        public CustomViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.itemImage);
            itemDesc = (TextView) itemView.findViewById(R.id.listItemDesc);
            currentBidValue = (TextView) itemView.findViewById(R.id.currentBidValue);
            totalBids = (TextView) itemView.findViewById(R.id.totalBids);
            timeLeft = (TextView) itemView.findViewById(R.id.timeLeft);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(getAdapterPosition());
        }
    }

}

