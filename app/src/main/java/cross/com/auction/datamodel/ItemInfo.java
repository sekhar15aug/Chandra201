package cross.com.auction.datamodel;

/**
 * Created by Arvind on 01-11-2015.
 */
public class ItemInfo {

    private int itemId;
    private String itemDesc;
    private int minBid;
    private int lastBid;
    private String lastUser;
    private long startTime;
    private long duration;

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    private int totalBids;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getMinBid() {
        return minBid;
    }

    public void setMinBid(int minBid) {
        this.minBid = minBid;
    }

    public int getLastBid() {
        return lastBid;
    }

    public void setLastBid(int lastBid) {
        this.lastBid = lastBid;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
