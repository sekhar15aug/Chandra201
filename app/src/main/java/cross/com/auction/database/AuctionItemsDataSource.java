package cross.com.auction.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cross.com.auction.datamodel.ItemInfo;
import cross.com.auction.datamodel.ItemManager;

/**
 * Created by Arvind on 31-10-2015.
 */
public class AuctionItemsDataSource {

    private static Context mContext;
    private SQLiteDatabase database;
    private AuctionItemsSQLiteHelper dbHelper;

    private String[] allColumns = {AuctionItemsSQLiteHelper.COLUMN_ID,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_DESC,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_MIN_BID,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID_USER,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_START_TIME,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_AUCTION_DURATION,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_TOTAL_BIDS};

    private String[] columnsToUpdate = {
            AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID_USER,
            AuctionItemsSQLiteHelper.COLUMN_ITEM_TOTAL_BIDS};

    private static AuctionItemsDataSource ourInstance = null;

    public static AuctionItemsDataSource getInstance() {
        if (null == ourInstance) {
            ourInstance = new AuctionItemsDataSource();
        }
        return ourInstance;
    }

    public void setAppContext(Context context) {
        mContext = context;
    }

    private AuctionItemsDataSource() {
    }

    public void open() throws SQLException {
        dbHelper = new AuctionItemsSQLiteHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<ItemInfo> getAllEntry() {
        List<ItemInfo> entryList = new ArrayList<>();

        Cursor cursor = database.query(AuctionItemsSQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ItemInfo entry = ItemManager.getInstance().cursorToEntry(cursor);
            entryList.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return entryList;
    }

    public ItemInfo createEntry(String itemDesc, int minBid, int lastBid,
                                String user, long startTime, long duration, int totalBids) {

        ContentValues values = new ContentValues();
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_DESC, itemDesc);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_MIN_BID, minBid);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID, lastBid);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID_USER, user);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_START_TIME, startTime);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_AUCTION_DURATION, duration);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_TOTAL_BIDS, totalBids);
        long insertId = database.insert(AuctionItemsSQLiteHelper.TABLE_NAME, null, values);

        if (-1 != insertId) {
            Cursor cursor = database.query(AuctionItemsSQLiteHelper.TABLE_NAME,
                    allColumns, AuctionItemsSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            ItemInfo newEntry = ItemManager.getInstance().cursorToEntry(cursor);
            cursor.close();
            return newEntry;
        }
        return null;
    }

    public void deleteEntry(String columnId) {
        database.delete(AuctionItemsSQLiteHelper.TABLE_NAME, AuctionItemsSQLiteHelper.COLUMN_ID
                + " = " + columnId, null);
    }

    public Cursor getMyEntry(String userId) {
        Cursor myCursor = database.query(AuctionItemsSQLiteHelper.TABLE_NAME, allColumns,
                AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID_USER + "='" + userId + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    public void updateEntry(int entryId, int currentBid, String user, int totalBids) {

        ContentValues values = new ContentValues();
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID, currentBid);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_LAST_BID_USER, user);
        values.put(AuctionItemsSQLiteHelper.COLUMN_ITEM_TOTAL_BIDS, totalBids);

        database.update(AuctionItemsSQLiteHelper.TABLE_NAME, values,
                AuctionItemsSQLiteHelper.COLUMN_ID + "=?", new String[]{String.valueOf(entryId)});
    }

}
