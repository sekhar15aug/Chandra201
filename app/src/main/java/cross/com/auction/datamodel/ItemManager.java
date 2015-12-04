package cross.com.auction.datamodel;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cross.com.auction.database.AuctionItemsDataSource;

/**
 * Created by Arvind on 01-11-2015.
 */
public class ItemManager {
    private static ItemManager ourInstance = new ItemManager();

    private List<ItemInfo> mItemInfoList;

    public static ItemManager getInstance() {
        return ourInstance;
    }

    private ItemManager() {
    }

    public List<ItemInfo> getItemInfoList() {
        mItemInfoList = AuctionItemsDataSource.getInstance().getAllEntry();
        return mItemInfoList;
    }

    public ItemInfo getCurrentItem(int position) {
        return mItemInfoList.get(position);
    }

    public List<ItemInfo> getItemInfoList(String userId) {
        Cursor cursor = AuctionItemsDataSource.getInstance().getMyEntry(userId);
        List<ItemInfo> itemList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemInfo entry = ItemManager.getInstance().cursorToEntry(cursor);
            itemList.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return itemList;
    }

    public ItemInfo cursorToEntry(Cursor cursor) {
        Log.i(getClass().getSimpleName(), "" + cursor.getCount());
        ItemInfo entry = new ItemInfo();
        entry.setItemId(cursor.getInt(0));
        entry.setItemDesc(cursor.getString(1));
        entry.setMinBid(cursor.getInt(2));
        entry.setLastBid(cursor.getInt(3));
        entry.setLastUser(cursor.getString(4));
        entry.setStartTime(cursor.getLong(5));
        entry.setDuration(cursor.getLong(6));
        entry.setTotalBids(cursor.getInt(7));
        return entry;
    }
}
