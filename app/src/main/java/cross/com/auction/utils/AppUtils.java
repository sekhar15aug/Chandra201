package cross.com.auction.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Arvind on 30-10-2015.
 */
public class AppUtils {

    public boolean isAuctionClosed(long startTime, long duration) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) > duration) {
            return true;
        }
        return false;
    }

    public static String getAuctionTimeLeft(long millis) {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        return time;
    }
}
