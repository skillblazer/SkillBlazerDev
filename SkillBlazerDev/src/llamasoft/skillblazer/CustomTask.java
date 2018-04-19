/** **********************************************************
 * Application Name: skillblazer
 * File Name: CustomTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/10/2018
 *
 * Description:
 *
 * This child class is one of the kind of tasks that represent
 * the different types of goals that define these
 * habits/skills.
 *
 * A custom task is one in which the user defines how often
 * to complete, such as reading 3 times a week (e.g. Monday,
 * Tuesday, and Wednesday).
 *
 * The CustomTask class contains a String array that holds
 * which days of the week the user selects to complete a task
 * (e.g. every Monday, Wednesday, and Friday). There are
 * methods to set and get these days of the week.
 ********************************************************** */
package llamasoft.skillblazer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomTask extends Task {

    private ArrayList<String> actualDaysInTask = new ArrayList<>();
    private int currentStreak; //current streak of completions
    private int bestStreak; //best streak of completions

    /*
     * 
     */
    public CustomTask() {
        super();
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * 
     */
    public CustomTask(String taskName) {
        super(taskName);
        this.type = "custom";
    } //end CustomTask constructor

    @Override
    public String toString() {
        System.out.println("Task: " + taskName + " days to perform: ");
        for (String day : this.actualDaysInTask) {
            System.out.println(day);
        }
        return super.toString() + "CurrentStreak is: " + currentStreak +
                "BestStreak is: " + bestStreak + actualDaysInTask.toString();

    }

    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate, ArrayList<String> arrayOfDays) {
        super(taskName, startDate);
        this.actualDaysInTask.addAll(arrayOfDays);
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate, ArrayList<String> arrayOfDays, int currentStreak,
            int bestStreak) {
        super(taskName, startDate);
        this.actualDaysInTask.addAll(arrayOfDays);
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.type = "custom";
    } //end CustomTask constructor

    /*
     *  Fully qualified constructor (needed for initializing objects stored on disk
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, int currentStreak,
            int bestStreak, ArrayList<String> daysInTask) {
        super(taskName, taskId, startDate, isCompleted, "custom");
        this.actualDaysInTask.addAll(daysInTask);
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
    } //end CustomTask constructor

    /*
     * 
     */
    public ArrayList<String> getDaysOfWeek() {
        return this.actualDaysInTask;
    } //end getDaysOfWeek method

    /*
     * 
     */
    public void setDaysOfWeek(ArrayList<String> listOfDays) {
        this.actualDaysInTask.addAll(listOfDays);
    } //end setDaysOfWeek method

    /*
     * 
     */
    public int getCurrentStreak() {
        return this.currentStreak;
    } //end getCurrentStreak method

    /*
     * 
     */
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    } //end setCurrentStreak method

    /*
     * 
     */
    public int getBestStreak() {
        return this.bestStreak;
    } //end getBestStreak method

    /*
     * 
     */
    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    } //end getBestStreak method


    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skblc";
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

        JSONArray jsonArray = new JSONArray();

        // add all the days listed in task.getDaysOfWeek() to a JSONArray
        for (String s : this.getDaysOfWeek()) {
            jsonArray.add(s);
        }

        // add the JSONArray to the JSONObject, name the array "days"
        jsonObject.put("days", jsonArray);

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    } //end method writeTaskToJSON()
}
