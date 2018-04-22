package llamasoft.skillblazer;

import org.json.simple.JSONObject;

import java.util.Calendar;

/**
 * The child classes listed above represent the different types of goals that
 * define these habits/skills.
 *
 * A daily task is one in which the user has the goal of completing every day,
 * such as exercising for 1 hour.
 *
 * The DailyTask class contains fields and methods associated with the
 * completion of a task on a daily basis. It includes fields for current streak
 * and best streak and methods to set and get these values. It also contains a
 * method to set a task as being completed for a given day and a method to get
 * that value.
 */
public class DailyTask extends Task {

    private int currentStreak;
    private int bestStreak;

    @Override
    public String toString() {
        return super.toString() + "CurrentStreak is: " + currentStreak +
                "BestStreak is: " + bestStreak;
    }

    public int getCurrentStreak() {
        return this.currentStreak;
    }

    public int getBestStreak() {
        return this.bestStreak;
    }

    public DailyTask() {
        super();
        this.type = "daily";
    }

    public DailyTask(String taskName) {
        super(taskName);
        this.type = "daily";
    }
    
     /*
     *  Fully qualified constructor (needed for initializing objects stored on disk
     */
    public DailyTask(String taskName, long taskId, Calendar startDate,
                     boolean isCompleted, int currentStreak, int bestStreak) {
        super(taskName, taskId, startDate, isCompleted, "daily");
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
    } //end DailyTask constructor


    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skbld";
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

    } // end method writeTaskToJSON()

    /* DO YOU NEED TO OVERRIDE the Task.getCurrentStreak() or
     * Task.getBestStreak() methods?  */
}
