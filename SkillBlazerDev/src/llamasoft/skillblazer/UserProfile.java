package llamasoft.skillblazer;

/**
 * Class which will contain information about the user.
 */

public class UserProfile {

    String taskName; // is this needed/best way to store this info?

    int currentStreak;
    private int startDate;
    boolean isCompleted;
    private String userName;

    public UserProfile() {}

    public UserProfile(String userName) {
        this.userName = userName;
    }

    public void getCurrentDay() {
        // display the current date in the top left of the application
    }

    public int getStartDate() {
        return startDate;
    }

    public String getUserName() {
        return userName;
    }

}
