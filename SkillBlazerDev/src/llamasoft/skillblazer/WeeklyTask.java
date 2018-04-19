package llamasoft.skillblazer;

/**
 *  The child classes listed above represent the different types of goals that
 *  define these habits/skills.
 *
 *  A weekly task is one in which the user has the goal of completing once a
 *  week, such as taking out the trash.
 *
 *The WeeklyTask class is similar to the DailyTask class in that it contains
 * fields for current streak and best streak, as well as methods to get these
 * values. However, for the WeeklyTask class, the current and best streak
 * pertains to the number of weeks in a row (as opposed to days in  row).
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeeklyTask extends Task {

    private int currentStreak;
    private int bestStreak;

    private Calendar startDate = new GregorianCalendar();

    public WeeklyTask() {
        super();
        this.type = "weekly";
    }

    public WeeklyTask(String taskName) {
        super(taskName);
        this.type = "weekly";
    }
    
    public WeeklyTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, int currentStreak, int bestStreak) {
        super(taskName, taskId, startDate, isCompleted, "weekly");
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
    } //end WeeklyTask constructor
    
    public Calendar getStartDate() {
        return this.startDate;
    }

    public int getCurrentStreak() {
        return this.currentStreak;
    }

    public int getBestStreak() {
        return this.bestStreak;
    }

    /* DO YOU NEED TO OVERRIDE the Task.getCurrentStreak() or
    Task.getBestStreak() methods? */
}
