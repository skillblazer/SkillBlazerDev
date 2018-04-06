package llamasoft.skillblazer;

import java.util.Calendar;

/**
 * The child classes listed above represent the different types of goals
 * that define these habits/skills.
 *
 * ...a custom task is one in which the user defines how often to complete, such
 * as reading 3 times a week (e.g. Monday, Tuesday, and Wednesday).
 *
 * the CustomTask class contains a boolean array that holds which days of the
 * week the user selects to complete a task (e.g. every Monday, Wednesday, and
 * Friday). There are methods to set and get these days of the week.
 *
 */

public class CustomTask extends Task {

    /* REMEMBER YOUR SUPERCLASS Members & Methods! */

    boolean[] daysOfWeek;

    int currentStreak;
    
    int bestStreak;

    public void setDaysOfWeek() {}

    public boolean[] getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public int getCurrentStreak() {
        return this.currentStreak;
    }

    public int getBestStreak() {
        return this.bestStreak;
    }

    public CustomTask() {}

    public CustomTask(String taskName) {
        this.taskName = taskName;
    }

    public CustomTask(String taskName, Calendar startDate) {
        this.taskName = taskName;
        this.startDate = startDate;
    }

}
