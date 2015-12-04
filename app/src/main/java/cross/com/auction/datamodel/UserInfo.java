package cross.com.auction.datamodel;

/**
 * Created by Arvind on 07-09-2015.
 */
public class UserInfo {

    private String email;
    private String name;
    private String mobileNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return mobileNo;
    }

    public void setNo(String no) {
        this.mobileNo = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
