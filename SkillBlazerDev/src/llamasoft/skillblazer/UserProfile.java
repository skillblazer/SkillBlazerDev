package llamasoft.skillblazer;

import java.util.Calendar;

/**
 * Class which will contain information about the user.
 */

public class UserProfile {

    private Calendar userStartDate;
    private String userName;

    public UserProfile() {}

    public UserProfile(String userName) {
        this.userName = userName;
    }

    public UserProfile(String userName, Calendar userStartDate) {
        this.userName = userName;
        this.userStartDate = userStartDate;
    }

    public void getCurrentDate() {
        // display the current date in the top left of the application
    }

    public Calendar getUserStartDate() {
        return userStartDate;
    }

    public String getUserName() {
        return userName;
    }

}
