package cross.com.auction.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Arvind on 31-10-2015.
 */
public class AuctionItemsSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "table_auction";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_DESC = "_item_desc";
    public static final String COLUMN_ITEM_MIN_BID = "_item_min_bid";
    public static final String COLUMN_ITEM_LAST_BID = "_item_last_bid";
    public static final String COLUMN_ITEM_LAST_BID_USER = "_item_last_bid_user";
    public static final String COLUMN_ITEM_START_TIME = "_item_start_time";
    public static final String COLUMN_ITEM_AUCTION_DURATION = "_item_auction_duration";
    public static final String COLUMN_ITEM_TOTAL_BIDS = "_item_total_bids";

    private static final String DATABASE_NAME = "auction.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ITEM_DESC + " TEXT, "
            + COLUMN_ITEM_MIN_BID + " INTEGER, "
            + COLUMN_ITEM_LAST_BID + " INTEGER, "
            + COLUMN_ITEM_LAST_BID_USER + " TEXT, "
            + COLUMN_ITEM_START_TIME + " LONG, "
            + COLUMN_ITEM_AUCTION_DURATION + " LONG, "
            + COLUMN_ITEM_TOTAL_BIDS + " INTEGER)";

    public AuctionItemsSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AuctionItemsSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}