package llamasoft.skillblazer;

/**
 *
 */

public class UserProfile {
    String taskName;
    int currentStreak;
    private int startDate;
    boolean isCompleted;
    private String userName;

    public UserProfile() {}

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
