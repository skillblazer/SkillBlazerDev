package llamasoft.skillblazer;

import java.util.Calendar;

/**
 * The child classes listed above represent the different types of goals that
 * define these habits/skills.
 *
 * A daily task is one in which the user has the
 * goal of completing every day, such as exercising for 1 hour.
 *
 * The DailyTask class contains fields and methods associated with the
 * completion of a task on a daily basis.
 * It includes fields for current streak and best streak and
 * methods to set and get these values. It also contains a method to set a task
 * as being completed for a given day and a method to get that value.
 */

public class DailyTask extends Task {

    /* REMEMBER ALL YOUR SUPERCLASS MEMBERS!!! */

    private boolean isCompleted = false;
    private String taskName;

    public boolean getIsCompleted() {
        return isCompleted;
    }

    protected void setCompleted() {
        isCompleted = true;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public DailyTask() {
        super();
    }

    public DailyTask(String taskName) {
        super();
        this.taskName = taskName;
    }

    public DailyTask(String taskName, Calendar startDate) {
        super();
        this.taskName = taskName;
        this.isCompleted = false;
    }

    /* DO YOU NEED TO OVERRIDE the Task.getCurrentStreak() or
     * Task.getBestStreak() methods?  */
}