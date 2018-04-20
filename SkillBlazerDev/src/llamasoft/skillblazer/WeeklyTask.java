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

import org.json.simple.JSONObject;

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


    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skblw";
        String fileName = filePrefix + taskSuffixNumber + ".json";

        Calendar cal = this.getStartDate();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("taskId", this.getTaskId());
        jsonObject.put("year", year);
        jsonObject.put("month", month);
        jsonObject.put("date", date);
        jsonObject.put("currentStreak", this.getCurrentStreak());
        jsonObject.put("bestStreak", this.getBestStreak());
        jsonObject.put("isCompleted", this.getIsCompleted());
        jsonObject.put("taskName", this.getTaskName());

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    } // end overloaded method writeTaskToJSON(WeeklyTask task)

    /* DO YOU NEED TO OVERRIDE the Task.getCurrentStreak() or
    Task.getBestStreak() methods? */
}
