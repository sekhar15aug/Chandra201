package cross.com.auction;

import android.app.Application;

import cross.com.auction.database.AuctionItemsDataSource;
import cross.com.auction.database.LoginDataSource;

/**
 * Created by Arvind on 01-11-2015.
 */
public class AuctionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //set application context
        LoginDataSource.getInstance().setAppContext(getApplicationContext());
        AuctionItemsDataSource.getInstance().setAppContext(getApplicationContext());

        //open login and items databases
        LoginDataSource.getInstance().open();
        AuctionItemsDataSource.getInstance().open();
    }
}
